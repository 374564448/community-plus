
const TokenKey = 'X-Token';

export function getToken() {
  return JSON.parse(localStorage.getItem(TokenKey));
}

export function setToken(token) {
  return localStorage.setItem("X-Token",JSON.stringify(token));
}

export function removeToken() {
  return localStorage.removeItem(TokenKey);
}
