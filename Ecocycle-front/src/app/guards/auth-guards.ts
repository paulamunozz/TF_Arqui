import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  let router = inject(Router);
  const token = localStorage.getItem('token');
  // 2. Verificar el estado de autenticación usando el servicio
  if (token!=null) {
    // Retorna 'true' para permitir el acceso si el token es válido
    return true;
  } else {
    alert("No accesible!")
    // 3. Si no hay token, redirigir al usuario
    router.navigate(['/login']);
    // Retorna 'false' para negar el acceso a la ruta solicitada
    return false;
  }
  return true;
};
