async function fetchDeleteMember(id) {
  return await fetch(`/api/members/${id}`, {
    method: 'DELETE'
  });
}

async function fetchDeleteGameCard(memberId, gameCardId) {
  return await fetch(`/api/members/${memberId}/game-cards/${gameCardId}`, {
    method: 'DELETE'
  });
}

async function fetchUpdateMember(memberId, data) {
  return await fetch(`/api/members/${memberId}`, {
    method: 'PUT',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(data),
  });
}

async function fetchCreateMember(data) {
  return await fetch(`/api/members`, {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(data),
  });
}

async function fetchAddGameCard(memberId, data) {
  return await fetch(`/api/members/${memberId}/game-cards`, {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(data),
  });
}
