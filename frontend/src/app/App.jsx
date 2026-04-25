import { useState } from 'react';
import ProjectList from '../components/Projects';
import ProjectDetail from '../components/ProjectDetail';

export default function App() {
  const [view, setView] = useState('projects');
  const [project, setProject] = useState(null);

  const handleSelect = (p) => {
    setProject(p);
    setView('detail');
  };

  const handleBack = () => {
    setProject(null);
    setView('projects');
  };

  return (
    <div className="app">
      <nav className="navbar">
        <div className="navbar-inner">
          {view === 'detail' && (
            <button className="back-btn" onClick={handleBack}>← Projects</button>
          )}
          <h1 className="navbar-title">
            {view === 'detail' ? project?.name : 'Project Tracker'}
          </h1>
        </div>
      </nav>
      <main className="page">
        {view === 'projects' ? (
          <ProjectList onSelect={handleSelect} />
        ) : (
          <ProjectDetail project={project} onUpdate={setProject} />
        )}
      </main>
    </div>
  );
}
