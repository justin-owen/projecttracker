import { api } from './client';

export async function getRisks(projectId) {
  const { data } = await api.get(`/api/risks/${projectId}`);
  return data;
}

export async function createRisk(risk) {
  const { data } = await api.post('/api/risks/create', risk);
  return data;
}

export async function updateRisk(riskId, risk) {
  const { data } = await api.put(`/api/risks/${riskId}`, risk);
  return data;
}

export async function deleteRisk(riskId) {
  await api.delete(`/api/risks/${riskId}`);
}
