import {Component, inject, OnInit, ViewChild, AfterViewInit} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import {
  MatDatepickerModule,
  MatDatepickerToggle,
  MatDatepicker,
  MatDatepickerInput
} from '@angular/material/datepicker';
import {DateAdapter, MAT_DATE_LOCALE, MatNativeDateModule} from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatPaginatorModule, MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatChipsModule } from '@angular/material/chips';
import { Router } from '@angular/router';

import { Reciclaje } from '../../model/reciclaje';
import { ReciclajeService } from '../../services/reciclaje-service';
import {MunicipalidadService} from '../../services/municipalidad-service';

@Component({
  selector: 'app-distrito-reciclaje',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatDatepickerModule,
    MatDatepickerToggle,
    MatDatepicker,
    MatDatepickerInput,
    MatNativeDateModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatPaginatorModule,
    MatChipsModule
  ],
  templateUrl: './municipalidad-reciclaje.html',
  styleUrl: './municipalidad-reciclaje.css',
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'es-PE' },
    DatePipe
  ]
})
export class MunicipalidadReciclaje {
  // ViewChild para acceder al paginador
  @ViewChild(MatPaginator) paginator: MatPaginator;

  // Formularios
  formFiltrosReciclaje: FormGroup;
  formFiltrosVecino: FormGroup;

  // Datos
  reciclajes: MatTableDataSource<Reciclaje> = new MatTableDataSource<Reciclaje>();
  puntajeTotal: number = 0;

  // Estados
  cargando: boolean = false;

  // Servicios
  private datePipe = inject(DatePipe);
  private fb = inject(FormBuilder);
  private reciclajeService = inject(ReciclajeService);
  private municipalidadService = inject(MunicipalidadService);
  private router = inject(Router);

  private userId = Number(localStorage.getItem('userId'));
  distrito: string = '';

  constructor(private dateAdapter: DateAdapter<Date>) {
    this.dateAdapter.setLocale('es-PE');

    // Formulario de filtros por registro de reciclaje
    this.formFiltrosReciclaje = this.fb.group({
      tipo: [null],
      metodo: [null],
      fechaInicio: [null],
      fechaFin: [null]
    });

    // Formulario de filtros por vecino
    this.formFiltrosVecino = this.fb.group({
      genero: [null],
      edadMin: [null],
      edadMax: [null]
    });
  }

  ngOnInit(): void {
    this.obtenerDistrito();
  }

  obtenerDistrito(): void {
    this.cargando = true;
    this.municipalidadService.buscarPorId(this.userId)
      .subscribe({
        next: (municipalidad) => {
          this.distrito = municipalidad.distrito;
          console.log('Distrito:', this.distrito);
          // Una vez obtenido el distrito, cargar los reciclajes
          this.cargarReciclajes();
        },
        error: (error) => {
          console.error('Error al obtener municipalidad:', error);
          this.cargando = false;
        }
      });
  }

  ngAfterViewInit(): void {
    this.reciclajes.paginator = this.paginator;

    // Configurar labels en español
    if (this.paginator) {
      this.paginator._intl.itemsPerPageLabel = 'Elementos por página:';
      this.paginator._intl.nextPageLabel = 'Siguiente página';
      this.paginator._intl.previousPageLabel = 'Página anterior';
      this.paginator._intl.firstPageLabel = 'Primera página';
      this.paginator._intl.lastPageLabel = 'Última página';
      this.paginator._intl.getRangeLabel = (page: number, pageSize: number, length: number) => {
        if (length === 0 || pageSize === 0) {
          return `0 de ${length}`;
        }
        length = Math.max(length, 0);
        const startIndex = page * pageSize;
        const endIndex = startIndex < length ?
          Math.min(startIndex + pageSize, length) :
          startIndex + pageSize;
        return `${startIndex + 1} - ${endIndex} de ${length}`;
      };
    }
  }

  /**
   * Carga los reciclajes del distrito con filtros
   */
  cargarReciclajes(): void {
    this.cargando = true;

    // Construir objeto de filtros combinando ambos formularios
    const filtros: any = {
      distrito: this.distrito
    };

    // Filtros de reciclaje
    if (this.formFiltrosReciclaje.value.tipo) {
      filtros.tipo = this.formFiltrosReciclaje.value.tipo;
    }

    if (this.formFiltrosReciclaje.value.metodo) {
      filtros.metodo = this.formFiltrosReciclaje.value.metodo;
    }

    // Formatear fechas si existen
    if (this.formFiltrosReciclaje.value.fechaInicio) {
      filtros.fechaInicio = this.datePipe.transform(
        this.formFiltrosReciclaje.value.fechaInicio,
        'yyyy-MM-dd'
      );
    }

    if (this.formFiltrosReciclaje.value.fechaFin) {
      filtros.fechaFin = this.datePipe.transform(
        this.formFiltrosReciclaje.value.fechaFin,
        'yyyy-MM-dd'
      );
    }

    // Filtros de vecino
    if (this.formFiltrosVecino.value.genero) {
      filtros.genero = this.formFiltrosVecino.value.genero;
    }

    if (this.formFiltrosVecino.value.edadMin) {
      filtros.edadMin = this.formFiltrosVecino.value.edadMin;
    }

    if (this.formFiltrosVecino.value.edadMax) {
      filtros.edadMax = this.formFiltrosVecino.value.edadMax;
    }

    console.log('Filtros aplicados:', filtros);

    this.reciclajeService.listarReciclajeFiltrado(filtros).subscribe({
      next: (data) => {
        this.reciclajes.data = data;

        // Reconectar el paginador después de actualizar los datos
        if (this.paginator) {
          this.reciclajes.paginator = this.paginator;
          this.paginator.firstPage();
        }

        this.calcularPuntajeTotal();
        console.log('Reciclajes del distrito cargados:', data);
        this.cargando = false;
      },
      error: (error) => {
        console.error('Error al listar reciclajes:', error);
        alert('Error al cargar los reciclajes del distrito');
        this.cargando = false;
      }
    });
  }

  aplicarFiltros(): void {
    this.cargarReciclajes();
  }

  /**
   * Limpia todos los filtros
   */
  limpiarFiltros(): void {
    this.formFiltrosReciclaje.reset({
      tipo: null,
      metodo: null,
      fechaInicio: null,
      fechaFin: null
    });

    this.formFiltrosVecino.reset({
      genero: null,
      edadMin: null,
      edadMax: null
    });

    this.cargarReciclajes();
  }

  /**
   * Calcula el puntaje total acumulado
   */
  calcularPuntajeTotal(): void {
    this.puntajeTotal = this.reciclajes.data.reduce((sum, r) => sum + (r.puntaje || 0), 0);
  }
}
