import { useState } from 'react';
import RequirementsPanel from './RequirementsPanel';
import RisksPanel from './RisksPanel';
import EffortsPanel from './EffortsPanel';
import MembersPanel from './MembersPanel';

const TABS = ['Requirements', 'Risks', 'Efforts', 'Members'];

export default function ProjectDetail({ project, onUpdate }) {
  const [activeTab, setActiveTab] = useState('Requirements');

  return (
    <>
      <div className="detail-header">
        <h2>{project.name}</h2>
        {project.description && <p className="detail-description">{project.description}</p>}
        <span className="detail-owner"><strong>Owner:</strong> {project.owner}</span>
      </div>

      <div className="tabs">
        {TABS.map((tab) => (
          <button
            key={tab}
            className={`tab-btn${activeTab === tab ? ' active' : ''}`}
            onClick={() => setActiveTab(tab)}
          >
            {tab}
          </button>
        ))}
      </div>

      {activeTab === 'Requirements' && <RequirementsPanel project={project} />}
      {activeTab === 'Risks' && <RisksPanel project={project} />}
      {activeTab === 'Efforts' && <EffortsPanel project={project} />}
      {activeTab === 'Members' && <MembersPanel project={project} onUpdate={onUpdate} />}
    </>
  );
}
