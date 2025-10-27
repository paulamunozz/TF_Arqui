import { Routes } from '@angular/router';
import {MunicipalidadEventos} from './us19-us20-municipalidad-eventos/municipalidad-eventos';
import {MunicipalidadRegistroEvento} from './us16-municipalidad-registro-evento/municipalidad-registro-evento';

export const routes: Routes = [
  {path:'', component:MunicipalidadEventos},
  {path:'eventos', component:MunicipalidadEventos},
  {path:'nuevo-evento', component:MunicipalidadRegistroEvento},
  {path:'**', redirectTo:''}
];
