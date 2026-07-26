import apiClient from './client';

export async function listHomes() {
  const response = await apiClient.get('/homes');
  return response.data;
}

export async function getHomeStatus(homeId) {
  const response = await apiClient.get(`/homes/${homeId}/status`);
  return response.data;
}

export async function getHomeTrend(homeId) {
  const response = await apiClient.get(`/homes/${homeId}/trend`);
  return response.data;
}

export async function registerHome(payload) {
  const response = await apiClient.post('/homes', payload);
  return response.data;
}

export async function getHomeAppliances(homeId) {
    const response = await apiClient.get(`/homes/${homeId}/appliances`);
    return response.data;
  }
  
  export async function getHomeRecommendations(homeId) {
    const response = await apiClient.get(`/homes/${homeId}/recommendations`);
    return response.data;
  }