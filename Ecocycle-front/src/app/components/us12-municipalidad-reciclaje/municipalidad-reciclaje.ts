import { Component } from '@angular/core';
import {MatCard, MatCardContent} from '@angular/material/card';
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from '@angular/material/datepicker';
import {MatFormField, MatSuffix} from '@angular/material/form-field';
import {MatInput, MatLabel} from '@angular/material/input';
import {MatSelect} from '@angular/material/select';
import {ReactiveFormsModule} from '@angular/forms';
import {MatNativeDateModule, MatOption} from '@angular/material/core';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-us12-municipalidad-reciclaje',
  imports: [
    MatCard,
    MatCardContent,
    MatDatepicker,
    MatDatepickerInput,
    MatDatepickerToggle,
    MatNativeDateModule,
    MatFormField,
    MatInput,
    MatLabel,
    MatOption,
    MatSelect,
    MatSuffix,
    ReactiveFormsModule,
    DatePipe,
  ],
  templateUrl: './municipalidad-reciclaje.html',
  styleUrl: './municipalidad-reciclaje.css',
})
export class MunicipalidadReciclaje {
  puntajeTotal: number = 1500;

  reciclajes= [
    {
      id: 1,
      peso: 5.00,
      tipo: 'Vidrio',
      fecha: new Date('2025-10-02'),
      metodo: 'En centros de la municipalidad'
    },
    {
      id: 2,
      peso: 1.40,
      tipo: 'Papel',
      fecha: new Date('2025-09-30'),
      metodo: 'En centros de la municipalidad'
    },
    {
      id: 3,
      peso: 1.35,
      tipo: 'Papel',
      fecha: new Date('2025-09-29'),
      metodo: 'En centros físiscos de la municipalidad'
    },
    {
      id: 4,
      peso: 2.30,
      tipo: 'Plástico',
      fecha: new Date('2025-09-20'),
      metodo: 'Camión de reciclaje de la municipalidad'
    },
    {
      id: 5,
      peso: 0.40,
      tipo: 'Papel',
      fecha: new Date('2025-09-10'),
      metodo: 'Camión de reciclaje de la municipalidad'
    }
  ];
}
