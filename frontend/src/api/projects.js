import { api } from './client';

export async function getProjects() {
  const { data } = await api.get('/api/projects/');
  return data;
}

export async function createProject(project) {
  const { data } = await api.post('/api/projects/create', project);
  return data;
}

export async function updateProject(id, project) {
  const { data } = await api.put(`/api/projects/${id}`, project);
  return data;
}

export async function deleteProject(id) {
  await api.delete(`/api/projects/${id}`);
}

export async function addMember(projectId, name) {
  const { data } = await api.post(`/api/projects/${projectId}/members`, { name });
  return data;
}

export async function deleteMember(projectId, name) {
  const { data } = await api.delete(`/api/projects/${projectId}/members`, { data: { name } });
  return data;
}

export async function editMember(projectId, oldName, newName) {
  const { data } = await api.put(`/api/projects/${projectId}/members`, { oldName, newName });
  return data;
}
