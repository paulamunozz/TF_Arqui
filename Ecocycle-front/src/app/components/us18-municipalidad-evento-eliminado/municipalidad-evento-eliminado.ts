import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-us18-municipalidad-evento-eliminado',
  standalone: true,
  imports: [],
  templateUrl: './municipalidad-evento-eliminado.html',
  styleUrl: './municipalidad-evento-eliminado.css',
})
export class MunicipalidadEventoEliminado {
  nombreEvento:  string = '';

  constructor(private router: Router) {
    const state = this.router.getCurrentNavigation()?.extras.state as { nombreEvento?: string };
    this.nombreEvento = state?.nombreEvento || 'Evento sin nombre';
  }

  volver() {
    this.router.navigate(['/eventos']);
  }
}

