import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-us25-municipalidad-evento-confirmacion',
  standalone: true,
  templateUrl: './municipalidad-evento-confirmacion.html',
  styleUrl: './municipalidad-evento-confirmacion.css',
})
export class MunicipalidadEventoConfirmacion {
  nombreEvento: string = '';

  constructor(private router: Router) {
    const state = this.router.getCurrentNavigation()?.extras.state as { nombreEvento?: string };
    this.nombreEvento = state?.nombreEvento || 'Evento sin nombre';
  }

  volver() {
    this.router.navigate(['/eventos']);
  }

  registrarOtro() {
    this.router.navigate(['/nuevo-evento']);
  }
}
