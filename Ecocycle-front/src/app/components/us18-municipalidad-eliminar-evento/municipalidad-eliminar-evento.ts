import { Component } from '@angular/core';
import {MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle} from '@angular/material/dialog';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-us18-municipalidad-eliminar-evento',
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatButton,
    MatDialogClose
  ],
  templateUrl: './municipalidad-eliminar-evento.html',
  styleUrl: './municipalidad-eliminar-evento.css',
})
export class MunicipalidadEliminarEvento {

}
