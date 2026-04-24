import { useEffect, useState } from "react";
import { getProjects } from "../api/project";
import ProjectForm from "./ProjectForm";

export default function ProjectsPage() {
  const [projects, setProjects] = useState([]);
  const [creatingProject, setCreatingProject] = useState(false);

  useEffect(() => {
    async function loadProjects() {
      try {
        const data = await getProjects();
        console.log(data);
        setProjects(data);
      } catch (err) {
        console.error("Failed to load projects", err);
      }
    }

    loadProjects();
  }, []);

  return (
    <div>
      <h1>Projects</h1>
        <button onClick={() => setCreatingProject(true)}>Create Project</button>
        {creatingProject && <ProjectForm toggleForm={setCreatingProject}/>}

      {projects.map((project) => (
        <div key={project.id}>
          <h2>{project.name}</h2>
          <p>{project.description}</p>
        </div>
      ))}
    </div>
  );
}