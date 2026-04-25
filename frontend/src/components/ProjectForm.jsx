import { useState } from 'react';
import { createProject, updateProject } from '../api/projects';
import Modal from './Modal';

export default function ProjectForm({ project, onClose }) {
  const [form, setForm] = useState({
    name: project?.name ?? '',
    description: project?.description ?? '',
    owner: project?.owner ?? '',
  });
  const [saving, setSaving] = useState(false);

  const set = (key) => (e) => setForm((f) => ({ ...f, [key]: e.target.value }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      if (project) {
        await updateProject(project.id, form);
      } else {
        await createProject({ ...form, members: [] });
      }
      onClose(true);
    } catch {
      setSaving(false);
    }
  };

  return (
    <Modal title={project ? 'Edit Project' : 'New Project'} onClose={() => onClose(false)}>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="form-label">Name</label>
          <input className="form-input" value={form.name} onChange={set('name')} placeholder="Project name" required />
        </div>
        <div className="form-group">
          <label className="form-label">Description</label>
          <textarea className="form-textarea" value={form.description} onChange={set('description')} placeholder="What is this project about?" />
        </div>
        <div className="form-group">
          <label className="form-label">Owner</label>
          <input className="form-input" value={form.owner} onChange={set('owner')} placeholder="Project owner" required />
        </div>
        <div className="form-actions">
          <button type="button" className="btn btn-secondary" onClick={() => onClose(false)}>Cancel</button>
          <button type="submit" className="btn btn-primary" disabled={saving}>
            {saving ? 'Saving…' : project ? 'Save Changes' : 'Create Project'}
          </button>
        </div>
      </form>
    </Modal>
  );
}
