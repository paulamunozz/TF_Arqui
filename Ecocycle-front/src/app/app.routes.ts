import { Routes } from '@angular/router';
import {MunicipalidadEventos} from './components/us19-us20-municipalidad-eventos/municipalidad-eventos';
import {
  MunicipalidadRegistroEvento
} from './components/us16-municipalidad-registro-evento/municipalidad-registro-evento';
import {
  MunicipalidadDetalleEvento
} from './components/us21-us22-us23-municipalidad-detalle-evento/municipalidad-detalle-evento';
import {VecinoRegistro} from './components/us05-vecino-registro/vecino-registro';
import {VecinoAutenticacion} from './components/vecino-autenticacion/vecino-autenticacion';
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

export const routes: Routes = [
  { path: '', component: MunicipalidadEventos },
  { path: 'eventos', component: MunicipalidadEventos },
  { path: 'nuevo-evento', component: MunicipalidadRegistroEvento },
  { path: 'evento/:id', component: MunicipalidadDetalleEvento },
  { path: 'registro', component: VecinoRegistro },
  { path: 'login', component: VecinoAutenticacion},
  { path: 'confirmacion-evento', component: VecinoEventoConfirmacion },
  { path: 'confirmacion-evento-mun', component: MunicipalidadEventoConfirmacion },
  { path: 'muni-evento-elim', component: MunicipalidadEventoEliminado },
  { path: 'reciclaje-confirmacion', component: VecinoReciclajeConfirmacion },
  { path: '**', redirectTo: '' }
];
