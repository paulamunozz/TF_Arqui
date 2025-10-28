import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-us13-vecino-reciclaje-confirmacion',
  standalone: true,
  templateUrl: './vecino-reciclaje-confirmacion.html',
  styleUrl: './vecino-reciclaje-confirmacion.css',
})
export class VecinoReciclajeConfirmacion {
  //se cambia cuando haya componente de reciclaje y el backend :D
  puntosGanados: number = 120;

  constructor(private router: Router) {}

  volver() {
    this.router.navigate(['/reciclajes']);
  }

  registrarOtro() {
    this.router.navigate(['/nuevo-reciclaje']);
  }
}
