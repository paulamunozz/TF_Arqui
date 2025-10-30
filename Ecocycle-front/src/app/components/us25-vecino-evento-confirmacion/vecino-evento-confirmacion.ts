import { Component } from '@angular/core';
import { Router} from '@angular/router';

@Component({
  selector: 'app-us25-vecino-evento-confirmacion',
  standalone: true,
  templateUrl: './vecino-evento-confirmacion.html',
  styleUrl: './vecino-evento-confirmacion.css',
})
export class VecinoEventoConfirmacion {
  constructor(private router: Router) {}

  volver() {
    //dps hay q adaptar a la pestaña eventos en modo vecino
    this.router.navigate(['/eventos']);
  }
}
