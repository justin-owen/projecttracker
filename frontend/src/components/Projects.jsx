import { useState, useEffect } from 'react';
import { getProjects, deleteProject } from '../api/projects';
import ProjectForm from './ProjectForm';

export default function ProjectList({ onSelect }) {
  const [projects, setProjects] = useState([]);
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
    async function fetchProjects() {
      const data = await getProjects();
      if (active) {
        setProjects(data);
        setLoading(false);
      }
    }
    fetchProjects();
    return () => { active = false; };
  }, [tick]);

  const handleDelete = async (e, id) => {
    e.stopPropagation();
    if (!window.confirm('Delete this project and all its data?')) return;
    await deleteProject(id);
    reload();
  };

  const handleEdit = (e, project) => {
    e.stopPropagation();
    setEditTarget(project);
    setShowForm(true);
  };

  const handleFormClose = (saved) => {
    setShowForm(false);
    setEditTarget(null);
    if (saved) reload();
  };

  return (
    <>
      <div className="page-header">
        <h2 className="page-title">Projects</h2>
        <button className="btn btn-primary" onClick={() => { setEditTarget(null); setShowForm(true); }}>
          + New Project
        </button>
      </div>

      {loading ? (
        <div className="loading">Loading projects…</div>
      ) : projects.length === 0 ? (
        <div className="empty-state">
          <p>No projects yet. Create your first project to get started.</p>
        </div>
      ) : (
        <div className="project-grid">
          {projects.map((project) => (
            <div key={project.id} className="project-card" onClick={() => onSelect(project)}>
              <div className="project-card-header">
                <h3>{project.name}</h3>
                <div className="card-actions">
                  <button className="btn btn-sm btn-secondary" onClick={(e) => handleEdit(e, project)}>
                    Edit
                  </button>
                  <button className="btn btn-sm btn-danger" onClick={(e) => handleDelete(e, project.id)}>
                    Delete
                  </button>
                </div>
              </div>
              <p className="project-description">{project.description || 'No description provided.'}</p>
              <div className="project-meta">
                <span><strong>Owner:</strong> {project.owner}</span>
                <span><strong>Members:</strong> {project.members?.length ?? 0}</span>
              </div>
            </div>
          ))}
        </div>
      )}

      {showForm && <ProjectForm project={editTarget} onClose={handleFormClose} />}
    </>
  );
}
