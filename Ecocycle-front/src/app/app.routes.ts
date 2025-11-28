import { Routes } from '@angular/router';
import {MunicipalidadEventos} from './components/us19-us20-municipalidad-eventos/municipalidad-eventos';
import {
  MunicipalidadRegistroEvento
} from './components/us16-municipalidad-registro-evento/municipalidad-registro-evento';
import {
  MunicipalidadDetalleEvento
} from './components/us21-us22-us23-municipalidad-detalle-evento/municipalidad-detalle-evento';
import {VecinoRegistro} from './components/us05-vecino-registro/vecino-registro';
import {VecinoAutenticacion} from './components/us01-municipalidad-vecino-autenticacion/vecino-autenticacion';
import {VecinoEventosDisponibles} from './components/us24-us25-vecino-eventos-disponibles/vecino-eventos-disponibles';
import {VecinoDetalleEventoDisponible} from './components/us21-us31-vecino-detalle-evento-disponible/vecino-detalle-evento-disponible';
import {VecinoEventosRegistrados} from './components/us27-vecino-eventos-registrados/vecino-eventos-registrados';
import {
  VecinoDetalleEventoRegistrado
} from './components/us21-us31-vecino-detalle-evento-registrado/vecino-detalle-evento-registrado';
import {VecinoPerfil} from './components/us08-vecino-perfil/vecino-perfil';
import {InicioComponent} from './components/us04-inicio-vecino/inicio-vecino';
import {InicioMunicipalidad} from './components/us04-inicio-municipalidad/inicio-municipalidad';
import {Contactanos} from './components/us04-contactanos/contactanos';
import {Consejos} from './components/us03-consejos/consejos';
import {
  MunicipalidadModificacionContrasena
} from './components/us37-municipalidad-modificacion-contrasena/municipalidad-modificacion-contrasena';
import {
  VecinoReciclaje
} from './components/us10-us11-us13-us14-us15-vecino-reciclaje/us10-us11-us13-us14-us15-vecino-reciclaje';
import {MunicipalidadReciclaje} from './components/us12-municipalidad-reciclaje/municipalidad-reciclaje';
import {
  MunicipalidadVecinoEstadisticas
} from './components/us34-us35-us36-municipalidad-vecino-estadisticas/municipalidad-vecino-estadisticas';
import {RankingVecino} from './components/us32-ranking-vecino/ranking-vecino';
import {RankingMunicipalidad} from './components/us33-ranking-municipalidad/ranking-municipalidad';
import {
  MunicipalidadModificarEvento
} from './components/us17-municipalidad-modificar-evento/us17-municipalidad-modificar-evento';
import {VecinoLogros} from './components/us09-vecino-logros/us09-vecino-logros';
import {VecinoModificar} from './components/us06-vecino-modificar/vecino-modificar';
import {authGuard} from './guards/auth-guards';

export const routes: Routes = [
  { path: '', component: VecinoAutenticacion },
  { path: 'registro', component: VecinoRegistro },
  { path: 'login', component: VecinoAutenticacion},

  { path: 'contacto', component: Contactanos},
  { path: 'estadisticas', component: MunicipalidadVecinoEstadisticas },
  { path: 'ranking-vecinos', component: RankingVecino },
  { path: 'ranking-municipalidades', component: RankingMunicipalidad },

  { path: 'inicio-vecino', component: InicioComponent, canActivate:[authGuard]},
  { path: 'consejos', component: Consejos, canActivate:[authGuard]},
  { path: 'mi-reciclaje', component: VecinoReciclaje, canActivate:[authGuard] },
  { path: 'eventos-disponibles', component: VecinoEventosDisponibles, canActivate:[authGuard] },
  { path: 'detalle-evento-disponible/:id', component: VecinoDetalleEventoDisponible, canActivate:[authGuard] },
  { path: 'mis-eventos', component: VecinoEventosRegistrados, canActivate:[authGuard] },
  { path: 'mis-eventos/:id', component: VecinoDetalleEventoRegistrado, canActivate:[authGuard] },
  { path: 'logros', component: VecinoLogros, canActivate:[authGuard] },
  { path: 'perfil', component: VecinoPerfil, canActivate:[authGuard] },
  { path: 'editar-perfil', component: VecinoModificar, canActivate:[authGuard] },

  { path: 'inicio-muni', component: InicioMunicipalidad, canActivate:[authGuard]},
  { path: 'eventos', component: MunicipalidadEventos, canActivate:[authGuard] },
  { path: 'evento/:id', component: MunicipalidadDetalleEvento, canActivate:[authGuard] },
  { path: 'nuevo-evento', component: MunicipalidadRegistroEvento, canActivate:[authGuard] },
  { path: 'modificar-evento/:id', component: MunicipalidadModificarEvento, canActivate:[authGuard] },
  { path: 'actualizar-contrasena', component: MunicipalidadModificacionContrasena, canActivate:[authGuard] },
  { path: 'reciclaje', component: MunicipalidadReciclaje, canActivate:[authGuard] },

  { path: '**', redirectTo: '' }
];
