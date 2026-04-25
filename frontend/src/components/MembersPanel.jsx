import { useState } from 'react';
import { addMember, deleteMember, editMember } from '../api/projects';

export default function MembersPanel({ project, onUpdate }) {
  const [newName, setNewName] = useState('');
  const [editing, setEditing] = useState(null); // { old, current }
  const [busy, setBusy] = useState(false);

  const members = project.members ?? [];

  const handleAdd = async (e) => {
    e.preventDefault();
    if (!newName.trim()) return;
    setBusy(true);
    try {
      const updated = await addMember(project.id, newName.trim());
      onUpdate(updated);
      setNewName('');
    } finally {
      setBusy(false);
    }
  };

  const handleDelete = async (name) => {
    if (!window.confirm(`Remove "${name}" from this project?`)) return;
    setBusy(true);
    try {
      const updated = await deleteMember(project.id, name);
      onUpdate(updated);
    } finally {
      setBusy(false);
    }
  };

  const handleEditSave = async () => {
    if (!editing) return;
    const trimmed = editing.current.trim();
    if (!trimmed || trimmed === editing.old) { setEditing(null); return; }
    setBusy(true);
    try {
      const updated = await editMember(project.id, editing.old, trimmed);
      onUpdate(updated);
      setEditing(null);
    } finally {
      setBusy(false);
    }
  };

  return (
    <div className="panel">
      <div className="panel-header">
        <span className="panel-title">Team Members</span>
      </div>
      <div className="panel-body">
        {members.length === 0 ? (
          <p style={{ color: 'var(--text-muted)', marginBottom: '1rem' }}>No members yet.</p>
        ) : (
          <div className="members-list">
            {members.map((name) => (
              <span key={name} className="member-chip">
                {editing?.old === name ? (
                  <>
                    <input
                      className="member-inline-input"
                      value={editing.current}
                      onChange={(e) => setEditing((ed) => ({ ...ed, current: e.target.value }))}
                      onKeyDown={(e) => {
                        if (e.key === 'Enter') handleEditSave();
                        if (e.key === 'Escape') setEditing(null);
                      }}
                      autoFocus
                    />
                    <button className="member-chip-btn" onClick={handleEditSave} disabled={busy} title="Save">✓</button>
                    <button className="member-chip-btn" onClick={() => setEditing(null)} title="Cancel">✕</button>
                  </>
                ) : (
                  <>
                    {name}
                    <button
                      className="member-chip-btn"
                      onClick={() => setEditing({ old: name, current: name })}
                      title="Rename"
                    >
                      ✎
                    </button>
                    <button
                      className="member-chip-btn"
                      onClick={() => handleDelete(name)}
                      disabled={busy}
                      title="Remove"
                    >
                      ✕
                    </button>
                  </>
                )}
              </span>
            ))}
          </div>
        )}

        <form className="member-add-row" onSubmit={handleAdd}>
          <input
            className="form-input"
            placeholder="Add member name…"
            value={newName}
            onChange={(e) => setNewName(e.target.value)}
          />
          <button type="submit" className="btn btn-primary" disabled={busy || !newName.trim()}>
            Add
          </button>
        </form>
      </div>
    </div>
  );
}
