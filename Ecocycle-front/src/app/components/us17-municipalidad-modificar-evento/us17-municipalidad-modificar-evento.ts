import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Evento } from '../../model/evento';
import { EventoService } from '../../services/evento-service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-us17-municipalidad-modificar-evento',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './us17-municipalidad-modificar-evento.html',
  styleUrls: ['./us17-municipalidad-modificar-evento.css']
})
export class MunicipalidadModificarEvento{
  formModificar: FormGroup;
  guardando: boolean = false;
  eventoId: number = 0;

  private fb = inject(FormBuilder);
  private eventoService = inject(EventoService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  constructor(private r:ActivatedRoute) {
    this.formModificar = this.fb.group({
      nombre: ['', Validators.required],
      bonificacion: ['', Validators.required],
      descripcion: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    // Obtener el ID del evento desde la ruta
    this.r.params.subscribe(params => {
      this.eventoId = +params['id'];
    })

    if (this.eventoId) {
      this.cargarEvento();
    } else {
      alert('No se encontró el evento');
      this.volver();
    }
  }

  cargarEvento(): void {
    this.eventoService.detalle(this.eventoId).subscribe({
      next: (data) => {
        console.log('Evento cargado:', data);
        this.formModificar.patchValue({
          nombre: data.nombre,
          bonificacion: data.bonificacion,
          descripcion: data.descripcion
        });
      },
      error: (error) => {
        console.error('Error al cargar evento:', error);
        alert('Error al cargar los datos del evento');
        this.volver();
      }
    });
  }

  onSubmit(): void {
    if (this.formModificar.valid) {
      this.guardando = true;

      let evento = new Evento();
      evento = this.formModificar.value;
      evento.idEvento = this.eventoId;

      console.log('Datos a modificar:', evento);

      this.eventoService.modificar(evento).subscribe({
        next: (data) => {
          console.log('Evento modificado:', data);
          alert('Evento modificado correctamente');
          this.router.navigate(['/evento/' + this.eventoId]);
        },
        error: (error) => {
          console.error('Error al modificar:', error);
          alert(error.error?.message || 'Error desconocido');
          this.guardando = false;
        }
      });
    } else {
      alert('Por favor, complete todos los campos requeridos.');
    }
  }

  volver(): void {
    this.router.navigate(['/evento/' + this.eventoId]);
  }
}
