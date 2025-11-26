import { Component } from '@angular/core';
import {MatButton} from '@angular/material/button';
import {MatDialogActions, MatDialogClose, MatDialogContent, MatDialogTitle} from '@angular/material/dialog';

@Component({
  selector: 'app-us07-vecino-eliminacion-cuenta',
  imports: [
    MatButton,
    MatDialogActions,
    MatDialogContent,
    MatDialogTitle,
    MatDialogClose
  ],
  templateUrl: './vecino-eliminacion-cuenta.html',
  styleUrl: './vecino-eliminacion-cuenta.css',
})
export class VecinoEliminacionCuenta {

}
