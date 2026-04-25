import { useState, useEffect } from "react";
import { getRisks, createRisk, updateRisk, deleteRisk } from "../api/risks";
import Modal from "./Modal";

const STATUSES = ["Open", "In Progress", "Mitigated", "Closed"];

const STATUS_BADGE = {
  open: "badge-red",
  "in progress": "badge-yellow",
  mitigated: "badge-green",
  closed: "badge-gray",
};

function statusBadge(status) {
  const cls = STATUS_BADGE[status?.toLowerCase()] ?? "badge-gray";
  return <span className={`badge ${cls}`}>{status}</span>;
}

function RiskForm({ projectId, risk, onClose }) {
  const [form, setForm] = useState({
    risk: risk?.risk ?? "",
    status: risk?.status ?? STATUSES[0],
    projectId,
  });
  const [saving, setSaving] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      if (risk?.id) {
        await updateRisk(risk.id, form);
      } else {
        await createRisk(form);
      }
      onClose(true);
    } catch {
      setSaving(false);
    }
  };

  return (
    <Modal
      title={risk ? "Edit Risk" : "Add Risk"}
      onClose={() => onClose(false)}
    >
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="form-label">Risk Description</label>
          <textarea
            className="form-textarea"
            value={form.risk}
            onChange={(e) => setForm((f) => ({ ...f, risk: e.target.value }))}
            placeholder="Describe the risk…"
            required
          />
        </div>
        <div className="form-group">
          <label className="form-label">Status</label>
          <select
            className="form-select"
            value={form.status}
            onChange={(e) => setForm((f) => ({ ...f, status: e.target.value }))}
          >
            {STATUSES.map((s) => (
              <option key={s} value={s}>
                {s}
              </option>
            ))}
          </select>
        </div>
        <div className="form-actions">
          <button
            type="button"
            className="btn btn-secondary"
            onClick={() => onClose(false)}
          >
            Cancel
          </button>
          <button type="submit" className="btn btn-primary" disabled={saving}>
            {saving ? "Saving…" : risk ? "Save Changes" : "Add Risk"}
          </button>
        </div>
      </form>
    </Modal>
  );
}

export default function RisksPanel({ project }) {
  const [risks, setRisks] = useState([]);
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
      const data = await getRisks(project.id);
      if (active) {
        setRisks(data);
        setLoading(false);
      }
    }
    fetchData();
    return () => {
      active = false;
    };
  }, [project.id, tick]);

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this risk?")) return;
    await deleteRisk(id);
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
          <span className="panel-title">Risks</span>
          <button
            className="btn btn-primary btn-sm"
            onClick={() => {
              setEditTarget(null);
              setShowForm(true);
            }}
          >
            + Add
          </button>
        </div>

        {loading ? (
          <div className="loading">Loading…</div>
        ) : risks.length === 0 ? (
          <div
            className="empty-state"
            style={{ margin: "1rem", border: "none" }}
          >
            No risks added yet.
          </div>
        ) : (
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>Risk</th>
                  <th style={{ width: "140px" }}>Status</th>
                  <th style={{ width: "110px" }}>Actions</th>
                </tr>
              </thead>
              <tbody>
                {risks.map((risk, i) => (
                  <tr key={risk.id ?? i}>
                    <td>{risk.risk}</td>
                    <td>{statusBadge(risk.status)}</td>
                    <td>
                      {risk.id && (
                        <div className="table-actions">
                          <button
                            className="btn btn-sm btn-secondary"
                            onClick={() => {
                              setEditTarget(risk);
                              setShowForm(true);
                            }}
                          >
                            Edit
                          </button>
                          <button
                            className="btn btn-sm btn-danger"
                            onClick={() => handleDelete(risk.id)}
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
        <RiskForm
          projectId={project.id}
          risk={editTarget}
          onClose={handleFormClose}
        />
      )}
    </>
  );
}
