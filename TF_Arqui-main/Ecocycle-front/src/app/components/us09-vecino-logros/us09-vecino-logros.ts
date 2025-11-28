import {Component, inject} from '@angular/core';
import {OnInit} from '@angular/core';
import {MatCard, MatCardContent, MatCardFooter} from '@angular/material/card';
import {NgForOf} from '@angular/common';
import {LogroService} from '../../services/logro-service';
import {Logro} from '../../model/logro';

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
  private logroService: LogroService = inject(LogroService);

  logros: Logro[] = [];

  ngOnInit() {
    this.cargarLogros();
  }

  cargarLogros(): void {
    const vecinoId = Number(localStorage.getItem('userId'));

    if (vecinoId) {
      console.log('Cargando logros para ID:', vecinoId);

      this.logroService.listarLogrosPorVecino(vecinoId).subscribe({
        next: (data) => {
          this.logros = data;
        },
        error: (err) => {
          console.error('Error al cargar los logros. ¿Token JWT válido?', err);
          this.logros = [];
        }
      });
    } else {
      console.warn('ID de usuario no encontrado en localStorage');
    }
  }

  get totalLogros(): number {
    return this.logros.length;
  }
}
