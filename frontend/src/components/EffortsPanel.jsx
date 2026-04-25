import { useState, useEffect } from 'react';
import { getEfforts, createEffort, updateEffort, deleteEffort, getEffortSummary } from '../api/efforts';
import { getRequirements } from '../api/requirements';
import Modal from './Modal';

const CATEGORIES = ['Design', 'Development', 'Testing', 'Management', 'Documentation', 'Other'];

function EffortForm({ projectId, members, effort, onClose }) {
  const today = new Date().toISOString().split('T')[0];
  const [form, setForm] = useState({
    memberName: effort?.memberName ?? (members[0] ?? ''),
    category: effort?.category ?? CATEGORIES[0],
    hours: effort?.hours ?? '',
    entryDate: effort?.entryDate ?? today,
    requirementId: effort?.requirementId ?? '',
    projectId,
  });
  const [requirements, setRequirements] = useState([]);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    getRequirements(projectId).then(setRequirements).catch(() => {});
  }, [projectId]);

  const set = (key) => (e) => setForm((f) => ({ ...f, [key]: e.target.value }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      const payload = {
        ...form,
        hours: parseFloat(form.hours),
        entryType: 'DAILY',
        projectId,
        requirementId: form.requirementId || null,
      };
      if (effort?.id) {
        await updateEffort(effort.id, payload);
      } else {
        await createEffort(payload);
      }
      onClose(true);
    } catch {
      setSaving(false);
    }
  };

  return (
    <Modal title={effort ? 'Edit Effort Entry' : 'Log Effort'} onClose={() => onClose(false)}>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="form-label">Member</label>
          <select className="form-select" value={form.memberName} onChange={set('memberName')} required>
            {members.length === 0 && <option value="">No members added yet</option>}
            {members.map((m) => <option key={m} value={m}>{m}</option>)}
          </select>
        </div>
        <div className="form-group">
          <label className="form-label">Requirement <span style={{ color: 'var(--text-muted)', fontWeight: 400 }}>(optional)</span></label>
          <select className="form-select" value={form.requirementId} onChange={set('requirementId')}>
            <option value="">— None —</option>
            {requirements.map((r) => (
              <option key={r.id} value={r.id}>[{r.type}] {r.requirement}</option>
            ))}
          </select>
        </div>
        <div className="form-grid-2">
          <div className="form-group">
            <label className="form-label">Category</label>
            <select className="form-select" value={form.category} onChange={set('category')}>
              {CATEGORIES.map((c) => <option key={c} value={c}>{c}</option>)}
            </select>
          </div>
          <div className="form-group">
            <label className="form-label">Hours</label>
            <input
              className="form-input"
              type="number"
              min="0.25"
              step="0.25"
              value={form.hours}
              onChange={set('hours')}
              required
            />
          </div>
          <div className="form-group" style={{ gridColumn: 'span 2' }}>
            <label className="form-label">Date</label>
            <input
              className="form-input"
              type="date"
              value={form.entryDate}
              onChange={set('entryDate')}
              required
            />
          </div>
        </div>
        <div className="form-actions">
          <button type="button" className="btn btn-secondary" onClick={() => onClose(false)}>Cancel</button>
          <button type="submit" className="btn btn-primary" disabled={saving || members.length === 0}>
            {saving ? 'Saving…' : effort ? 'Save Changes' : 'Log Effort'}
          </button>
        </div>
      </form>
    </Modal>
  );
}

export default function EffortsPanel({ project }) {
  const [efforts, setEfforts] = useState([]);
  const [summary, setSummary] = useState({});
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editTarget, setEditTarget] = useState(null);
  const [tick, setTick] = useState(0);

  const reload = () => {
    setLoading(true);
    setTick((t) => t + 1);
  };

  useEffect(() => {
    let active = true;
    async function fetchData() {
      const [data, sum] = await Promise.all([
        getEfforts(project.id),
        getEffortSummary(project.id),
      ]);
      if (active) {
        setEfforts(data);
        setSummary(sum);
        setLoading(false);
      }
    }
    fetchData();
    return () => { active = false; };
  }, [project.id, tick]);

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this effort entry?')) return;
    await deleteEffort(id);
    reload();
  };

  const handleFormClose = (saved) => {
    setShowForm(false);
    setEditTarget(null);
    if (saved) reload();
  };

  const totalHours = Object.values(summary).reduce((a, b) => a + b, 0);
  const summaryEntries = Object.entries(summary);

  return (
    <>
      {!loading && summaryEntries.length > 0 && (
        <div className="summary-row">
          <div className="summary-card total">
            <div className="s-label">Total Hours</div>
            <div className="s-value">{totalHours.toFixed(1)}</div>
          </div>
          {summaryEntries.map(([category, hours]) => (
            <div key={category} className="summary-card">
              <div className="s-label">{category}</div>
              <div className="s-value">{hours.toFixed(1)}</div>
            </div>
          ))}
        </div>
      )}

      <div className="panel">
        <div className="panel-header">
          <span className="panel-title">Effort Log</span>
          <button className="btn btn-primary btn-sm" onClick={() => { setEditTarget(null); setShowForm(true); }}>
            + Log Effort
          </button>
        </div>

        {loading ? (
          <div className="loading">Loading…</div>
        ) : efforts.length === 0 ? (
          <div className="empty-state" style={{ margin: '1rem', border: 'none' }}>
            No effort entries yet.
          </div>
        ) : (
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>Member</th>
                  <th>Category</th>
                  <th>Hours</th>
                  <th>Date</th>
                  <th style={{ width: '110px' }}>Actions</th>
                </tr>
              </thead>
              <tbody>
                {efforts.map((effort) => (
                  <tr key={effort.id}>
                    <td>{effort.memberName}</td>
                    <td><span className="badge badge-blue">{effort.category}</span></td>
                    <td>{effort.hours}</td>
                    <td>{effort.entryDate}</td>
                    <td>
                      <div className="table-actions">
                        <button
                          className="btn btn-sm btn-secondary"
                          onClick={() => { setEditTarget(effort); setShowForm(true); }}
                        >
                          Edit
                        </button>
                        <button
                          className="btn btn-sm btn-danger"
                          onClick={() => handleDelete(effort.id)}
                        >
                          Del
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {showForm && (
        <EffortForm
          projectId={project.id}
          members={project.members ?? []}
          effort={editTarget}
          onClose={handleFormClose}
        />
      )}
    </>
  );
}
