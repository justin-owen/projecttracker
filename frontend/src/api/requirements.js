import { api } from './client';

export async function getRequirements(projectId) {
  const { data } = await api.get(`/api/requirements/${projectId}`);
  return data;
}

export async function createRequirement(requirement) {
  const { data } = await api.post('/api/requirements/create', requirement);
  return data;
}

export async function updateRequirement(requirementId, requirement) {
  const { data } = await api.put(`/api/requirements/${requirementId}`, requirement);
  return data;
}

export async function deleteRequirement(requirementId) {
  await api.delete(`/api/requirements/${requirementId}`);
}
