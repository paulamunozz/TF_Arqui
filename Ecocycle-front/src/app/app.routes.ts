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
import {VecinoEventoConfirmacion} from './components/us25-vecino-evento-confirmacion/vecino-evento-confirmacion';
import {
  MunicipalidadEventoConfirmacion
} from './components/us16-municipalidad-evento-confirmacion/municipalidad-evento-confirmacion';
import {
  MunicipalidadEventoEliminado
} from './components/us18-municipalidad-evento-eliminado/municipalidad-evento-eliminado';
import {
  VecinoReciclajeConfirmacion
} from './components/us13-vecino-reciclaje-confirmacion/vecino-reciclaje-confirmacion';
import {VecinoEventosDisponibles} from './components/us24-us25-vecino-eventos-disponibles/vecino-eventos-disponibles';
import {VecinoDetalleEventoDisponible} from './components/us21-us31-vecino-detalle-evento-disponible/vecino-detalle-evento-disponible';
import {VecinoEventosRegistrados} from './components/us27-vecino-eventos-registrados/vecino-eventos-registrados';
import {
  VecinoDetalleEventoRegistrado
} from './components/us21-us26-us31-vecino-detalle-evento-registrado/vecino-detalle-evento-registrado';
import {PerfilModificarEliminarComponent} from './components/us06-perfil-modificar-eliminar/perfil-modificar-eliminar';
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

export const routes: Routes = [
  { path: '', component: VecinoAutenticacion },
  { path: 'registro', component: VecinoRegistro },
  { path: 'login', component: VecinoAutenticacion},


  { path: 'mi-reciclaje', component: VecinoReciclaje },
  { path: 'eventos', component: MunicipalidadEventos },
  { path: 'evento/:id', component: MunicipalidadDetalleEvento },
  { path: 'nuevo-evento', component: MunicipalidadRegistroEvento },
  { path: 'confirmacion-evento', component: VecinoEventoConfirmacion },
  { path: 'confirmacion-evento-mun', component: MunicipalidadEventoConfirmacion },
  { path: 'muni-evento-elim', component: MunicipalidadEventoEliminado },
  { path: 'reciclaje-confirmacion', component: VecinoReciclajeConfirmacion },
  { path: 'eventos-disponibles', component: VecinoEventosDisponibles },
  { path: 'detalle-evento-disponible/:id', component: VecinoDetalleEventoDisponible },
  { path: 'mis-eventos', component: VecinoEventosRegistrados },
  { path: 'detalle-evento-registrado/:id', component: VecinoDetalleEventoRegistrado },
  { path: 'perfil', component: PerfilModificarEliminarComponent},
  { path: 'inicio-vecino', component: InicioComponent},
  { path: 'inicio-muni', component: InicioMunicipalidad},
  { path: 'contacto', component: Contactanos},
  { path: 'consejos', component: Consejos},
  { path: 'actualizar-contrasena', component: MunicipalidadModificacionContrasena },
  { path: 'reciclaje', component: MunicipalidadReciclaje },


  { path: 'estadisticas', component: MunicipalidadVecinoEstadisticas },
  { path: 'ranking-vecinos', component: RankingVecino },
  { path: 'ranking-municipalidades', component: RankingMunicipalidad },

  { path: '**', redirectTo: '' }
];
