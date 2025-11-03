import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatChipsModule } from '@angular/material/chips';

interface Reciclaje {
  id: number;
  peso: number;
  tipo: string;
  fecha: Date;
  metodo: string;
}

@Component({
  selector: 'app-us10-us11-us13-us14-us15-vecino-reciclaje',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatToolbarModule,
    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatChipsModule
  ],
  templateUrl: './us10-us11-us13-us14-us15-vecino-reciclaje.html',
  styleUrls: ['./us10-us11-us13-us14-us15-vecino-reciclaje.css']
})
export class VecinoReciclaje implements OnInit {
  // Datos
  reciclajes: Reciclaje[] = [
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

  reciclajesFiltrados: Reciclaje[] = [];
  puntajeTotal: number = 1500;

  // Filtros
  filtroTipo: string = '';
  filtroMetodo: string = '';
  fechaMin: string = '';
  fechaMax: string = '';

  // Modal
  mostrarModal: boolean = false;
  modoEdicion: boolean = false;
  reciclajeForm: any = {
    id: null,
    peso: null,
    tipo: '',
    fecha: '',
    metodo: ''
  };

  ngOnInit(): void {
    this.reciclajesFiltrados = [...this.reciclajes];
    this.calcularPuntajeTotal();
  }

  // Filtros
  aplicarFiltros(): void {
    this.reciclajesFiltrados = this.reciclajes.filter(reciclaje => {
      let cumpleFiltros = true;

      // Filtro por tipo
      if (this.filtroTipo && reciclaje.tipo !== this.filtroTipo) {
        cumpleFiltros = false;
      }

      // Filtro por metodo
      if (this.filtroMetodo && reciclaje.metodo !== this.filtroMetodo) {
        cumpleFiltros = false;
      }

      // Filtro por fecha mínima
      if (this.fechaMin) {
        const fechaMinDate = new Date(this.fechaMin);
        if (reciclaje.fecha < fechaMinDate) {
          cumpleFiltros = false;
        }
      }

      // Filtro por fecha máxima
      if (this.fechaMax) {
        const fechaMaxDate = new Date(this.fechaMax);
        if (reciclaje.fecha > fechaMaxDate) {
          cumpleFiltros = false;
        }
      }

      return cumpleFiltros;
    });
  }

  // Calcular puntaje total
  calcularPuntajeTotal(): void {
    // Ejemplo: 100 puntos por cada kg reciclado
    const pesoTotal = this.reciclajes.reduce((sum, r) => sum + r.peso, 0);
    this.puntajeTotal = Math.round(pesoTotal * 100);
  }

  // Modal
  abrirModalCrear(): void {
    this.modoEdicion = false;
    this.reciclajeForm = {
      id: null,
      peso: null,
      tipo: '',
      fecha: '',
      metodo: ''
    };
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
  }

  // CRUD
  guardarReciclaje(): void {
    if (this.modoEdicion) {
      // Editar existente
      const index = this.reciclajes.findIndex(r => r.id === this.reciclajeForm.id);
      if (index !== -1) {
        this.reciclajes[index] = {
          ...this.reciclajeForm,
          fecha: new Date(this.reciclajeForm.fecha)
        };
      }
    } else {
      // Crear nuevo
      const nuevoReciclaje: Reciclaje = {
        id: this.generarNuevoId(),
        peso: this.reciclajeForm.peso,
        tipo: this.reciclajeForm.tipo,
        fecha: new Date(this.reciclajeForm.fecha),
        metodo: this.reciclajeForm.metodo
      };
      this.reciclajes.push(nuevoReciclaje);
    }

    this.calcularPuntajeTotal();
    this.aplicarFiltros();
    this.cerrarModal();
  }

  editarReciclaje(reciclaje: Reciclaje): void {
    this.modoEdicion = true;
    this.reciclajeForm = {
      id: reciclaje.id,
      peso: reciclaje.peso,
      tipo: reciclaje.tipo,
      fecha: this.formatearFechaParaInput(reciclaje.fecha),
      metodo: reciclaje.metodo
    };
    this.mostrarModal = true;
  }

  eliminarReciclaje(id: number): void {
    if (confirm('¿Estás seguro de eliminar este reciclaje?')) {
      this.reciclajes = this.reciclajes.filter(r => r.id !== id);
      this.calcularPuntajeTotal();
      this.aplicarFiltros();
    }
  }

  // Utilidades
  generarNuevoId(): number {
    return this.reciclajes.length > 0
      ? Math.max(...this.reciclajes.map(r => r.id)) + 1
      : 1;
  }

  formatearFechaParaInput(fecha: Date): string {
    const year = fecha.getFullYear();
    const month = String(fecha.getMonth() + 1).padStart(2, '0');
    const day = String(fecha.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }
}
