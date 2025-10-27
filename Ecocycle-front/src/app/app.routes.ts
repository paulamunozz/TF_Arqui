import { Routes } from '@angular/router';
import {MunicipalidadEventos} from './us19-us20-municipalidad-eventos/municipalidad-eventos';

export const routes: Routes = [
  {path:'', component:MunicipalidadEventos},
  {path:'eventos', component:MunicipalidadEventos},
  {path:'**', redirectTo:''}
];
