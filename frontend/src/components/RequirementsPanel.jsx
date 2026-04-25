import { useState, useEffect } from 'react';
import { getRequirements, createRequirement, updateRequirement, deleteRequirement } from '../api/requirements';
import Modal from './Modal';

const TYPES = ['Functional', 'Non-Functional'];

function RequirementForm({ projectId, requirement, onClose }) {
  const [form, setForm] = useState({
    type: requirement?.type ?? TYPES[0],
    requirement: requirement?.requirement ?? '',
    projectId,
  });
  const [saving, setSaving] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      if (requirement?.id) {
        await updateRequirement(requirement.id, form);
      } else {
        await createRequirement(form);
      }
      onClose(true);
    } catch {
      setSaving(false);
    }
  };

  return (
    <Modal title={requirement ? 'Edit Requirement' : 'Add Requirement'} onClose={() => onClose(false)}>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="form-label">Type</label>
          <select
            className="form-select"
            value={form.type}
            onChange={(e) => setForm((f) => ({ ...f, type: e.target.value }))}
          >
            {TYPES.map((t) => <option key={t} value={t}>{t}</option>)}
          </select>
        </div>
        <div className="form-group">
          <label className="form-label">Requirement</label>
          <textarea
            className="form-textarea"
            value={form.requirement}
            onChange={(e) => setForm((f) => ({ ...f, requirement: e.target.value }))}
            placeholder="Describe the requirement…"
            required
          />
        </div>
        <div className="form-actions">
          <button type="button" className="btn btn-secondary" onClick={() => onClose(false)}>Cancel</button>
          <button type="submit" className="btn btn-primary" disabled={saving}>
            {saving ? 'Saving…' : requirement ? 'Save Changes' : 'Add Requirement'}
          </button>
        </div>
      </form>
    </Modal>
  );
}

function typeBadge(type) {
  const isNF = type?.toLowerCase().startsWith('non');
  return <span className={`badge ${isNF ? 'badge-yellow' : 'badge-blue'}`}>{type}</span>;
}

export default function RequirementsPanel({ project }) {
  const [reqs, setReqs] = useState([]);
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
      const data = await getRequirements(project.id);
      if (active) {
        setReqs(data);
        setLoading(false);
      }
    }
    fetchData();
    return () => { active = false; };
  }, [project.id, tick]);

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this requirement?')) return;
    await deleteRequirement(id);
    reload();
  };

  const handleFormClose = (saved) => {
    setShowForm(false);
    setEditTarget(null);
    if (saved) reload();
  };

  return (
    <>
      <div className="panel">
        <div className="panel-header">
          <span className="panel-title">Requirements</span>
          <button className="btn btn-primary btn-sm" onClick={() => { setEditTarget(null); setShowForm(true); }}>
            + Add
          </button>
        </div>

        {loading ? (
          <div className="loading">Loading…</div>
        ) : reqs.length === 0 ? (
          <div className="empty-state" style={{ margin: '1rem', border: 'none' }}>
            No requirements added yet.
          </div>
        ) : (
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th style={{ width: '160px' }}>Type</th>
                  <th>Requirement</th>
                  <th style={{ width: '110px' }}>Actions</th>
                </tr>
              </thead>
              <tbody>
                {reqs.map((req, i) => (
                  <tr key={req.id ?? i}>
                    <td>{typeBadge(req.type)}</td>
                    <td>{req.requirement}</td>
                    <td>
                      {req.id && (
                        <div className="table-actions">
                          <button
                            className="btn btn-sm btn-secondary"
                            onClick={() => { setEditTarget(req); setShowForm(true); }}
                          >
                            Edit
                          </button>
                          <button
                            className="btn btn-sm btn-danger"
                            onClick={() => handleDelete(req.id)}
                          >
                            Del
                          </button>
                        </div>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {showForm && (
        <RequirementForm projectId={project.id} requirement={editTarget} onClose={handleFormClose} />
      )}
    </>
  );
}
