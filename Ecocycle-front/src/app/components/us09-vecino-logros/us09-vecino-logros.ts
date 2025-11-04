import { Component } from '@angular/core';
import {MatCard, MatCardContent, MatCardFooter, MatCardHeader, MatCardTitle} from '@angular/material/card';
import {NgForOf} from '@angular/common';

interface Logro {
  titulo: string;
  descripcion: string;
}

@Component({
  selector: 'app-us09-vecino-logros',
  imports: [
    MatCardContent,
    MatCard,
    MatCardFooter,
    NgForOf
  ],
  templateUrl: './us09-vecino-logros.html',
  styleUrl: './us09-vecino-logros.css',
})
export class VecinoLogros {
  logros: Logro[] = [
    {
      titulo: 'La más contribuidora',
      descripcion: 'Se obtuvo cuando recicló por 30 días seguidos'
    },
    {
      titulo: 'Mis primeros 100',
      descripcion: 'Se obtuvo cuando registró sus primeros 100 desechos'
    },
    {
      titulo: 'La más recicladora',
      descripcion: 'Se obtuvo cuando recicló por 5 días seguidos'
    }
  ];

  get totalLogros(): number {
    return this.logros.length;
  }

}
