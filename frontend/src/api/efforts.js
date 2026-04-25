import { api } from './client';

export async function getEfforts(projectId) {
  const { data } = await api.get(`/api/efforts/${projectId}`);
  return data;
}

export async function createEffort(effort) {
  const { data } = await api.post('/api/efforts/create', effort);
  return data;
}

export async function updateEffort(effortId, effort) {
  const { data } = await api.put(`/api/efforts/${effortId}`, effort);
  return data;
}

export async function deleteEffort(effortId) {
  await api.delete(`/api/efforts/${effortId}`);
}

export async function getEffortSummary(projectId) {
  const { data } = await api.get(`/api/efforts/${projectId}/summary`);
  return data;
}
