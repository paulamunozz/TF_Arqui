import {Component, inject, OnInit, ViewChild, AfterViewInit} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatToolbarModule } from '@angular/material/toolbar';
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
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatChipsModule } from '@angular/material/chips';
import { MatTableDataSource } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import {MatPaginatorModule, MatPaginator, PageEvent} from '@angular/material/paginator';
import { Router } from '@angular/router';

import { Reciclaje } from '../../model/reciclaje';
import { Vecino } from '../../model/vecino';
import { ReciclajeService } from '../../services/reciclaje-service';
import { VecinoService } from '../../services/vecino-service';

@Component({
  selector: 'app-us10-us11-us13-us14-us15-vecino-reciclaje',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatToolbarModule,
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
    MatTooltipModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatPaginatorModule
  ],
  templateUrl: './us10-us11-us13-us14-us15-vecino-reciclaje.html',
  styleUrls: ['./us10-us11-us13-us14-us15-vecino-reciclaje.css'],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'es-PE' },
    DatePipe
  ]
})
export class VecinoReciclaje{
    // Formularios
  formRegistro: FormGroup;
  formFiltro: FormGroup;

  // Datos
  reciclajes: MatTableDataSource<Reciclaje> = new MatTableDataSource<Reciclaje>();
  vecino: Vecino = new Vecino();
  // puntajeTotal ya no es necesario, se usa vecino.puntajetotal

  // Modal
  mostrarModal: boolean = false;
  modoEdicion: boolean = false;
  reciclajeForm: any = {
    id: null,
    peso: null,
    tipo: '',
    metodo: ''
  };

  // Estados
  cargando: boolean = false;
  guardando: boolean = false;

  // Variable para la imagen del botón crear
  imgSrcCrear: string = '/btn-crear-1.png';

  // Servicios
  private datePipe = inject(DatePipe);
  private fb = inject(FormBuilder);
  private reciclajeService = inject(ReciclajeService);
  private vecinoService = inject(VecinoService);
  private router = inject(Router);
  private userId = Number(localStorage.getItem('userId'));

  constructor(private dateAdapter: DateAdapter<Date>) {
    this.dateAdapter.setLocale('es-PE');

    // Formulario de registro
    this.formRegistro = this.fb.group({
      peso: ['', [Validators.required, Validators.min(0.01)]],
      tipo: ['', Validators.required],
      metodo: ['', Validators.required],
    });

    // Formulario de filtros - Inicializar con valores vacíos o null
    this.formFiltro = this.fb.group({
      tipo: [null],        // null en lugar de ''
      metodo: [null],      // null en lugar de ''
      fechaInicio: [null], // null en lugar de ''
      fechaFin: [null],    // null en lugar de ''
    });
  }

  ngOnInit(): void {
    this.cargando = true;
    this.vecinoService.buscarPorID(this.userId).subscribe({
      next: (data) => {
        this.vecino = data;
        console.log('Vecino cargado:', data);
        console.log('Puntaje total del vecino:', data.puntajetotal);
        this.listarPorVecino();
      },
      error: (error) => {
        console.error('Error al cargar vecino:', error);
        alert('Error al cargar los datos del usuario');
        this.cargando = false;
      }
    });


  }

  /**
   * Registra un nuevo reciclaje
   */
  onSubmit(): void {
    if (this.formRegistro.valid) {
      this.guardando = true;

      let reciclaje = new Reciclaje();
      reciclaje.peso = this.formRegistro.value.peso;
      reciclaje.tipo = this.formRegistro.value.tipo;
      reciclaje.metodo = this.formRegistro.value.metodo;
      reciclaje.vecinoId = this.userId;

      console.log('Datos a registrar:', reciclaje);

      this.reciclajeService.registrar(reciclaje).subscribe({
        next: (data) => {
          console.log('Reciclaje registrado:', data);
          alert('¡Reciclaje registrado exitosamente!');

          // Limpiar formulario
          this.formRegistro.reset();

          // Recargar datos del vecino para actualizar el puntaje
          this.ngOnInit();

          // Cerrar modal si está abierto
          this.cerrarModal();

          this.guardando = false;
        },
        error: (error) => {
          console.error('Error al registrar:', error);
          alert('Error al registrar el reciclaje. Intente nuevamente.');
          this.guardando = false;
        }
      });
    } else {
      alert('Por favor, complete todos los campos correctamente');
      this.formRegistro.markAllAsTouched();
    }
  }

  /**
   * Lista los reciclajes del vecino con filtros
   */
  listarPorVecino(): void {
    this.cargando = true;

    const filtros: any = {
      vecinoId: this.userId
    };

    // Solo agregar filtros si tienen valor
    if (this.formFiltro.value.tipo) {
      filtros.tipo = this.formFiltro.value.tipo;
    }

    if (this.formFiltro.value.metodo) {
      filtros.metodo = this.formFiltro.value.metodo;
    }

    // Formatear fechas solo si existen
    if (this.formFiltro.value.fechaInicio) {
      filtros.fechaInicio = this.datePipe.transform(this.formFiltro.value.fechaInicio, 'yyyy-MM-dd');
    }

    if (this.formFiltro.value.fechaFin) {
      filtros.fechaFin = this.datePipe.transform(this.formFiltro.value.fechaFin, 'yyyy-MM-dd');
    }

    console.log('Filtros aplicados:', filtros);

    this.reciclajeService.listarPorVecino(filtros).subscribe({
      next: (data) => {
        this.reciclajes.data = data;

        console.log('Reciclajes cargados:', data);
        this.cargando = false;
      },
      error: (error) => {
        console.error('Error al listar reciclajes:', error);
        alert('Error al cargar los reciclajes');
        this.cargando = false;
      }
    });
  }

  /**
   * Aplica los filtros (llama a listarPorVecino)
   */
  aplicarFiltros(): void {
    this.listarPorVecino();
  }

  /**
   * Limpia todos los filtros
   */
  limpiarFiltros(): void {
    this.formFiltro.reset({
      tipo: null,
      metodo: null,
      fechaInicio: null,
      fechaFin: null
    });
    this.listarPorVecino();
  }

  // ==================== MODAL ====================

  abrirModalCrear(): void {
    this.modoEdicion = false;
    this.reciclajeForm = {
      id: null,
      peso: null,
      tipo: '',
      metodo: ''
    };
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
    this.reciclajeForm = {
      id: null,
      peso: null,
      tipo: '',
      metodo: ''
    };
  }

  // ==================== CRUD ====================

  /**
   * Guarda un reciclaje (crear o editar desde modal)
   */
  guardarReciclaje(): void {
    if (!this.reciclajeForm.peso || !this.reciclajeForm.tipo || !this.reciclajeForm.metodo) {
      alert('Por favor, complete todos los campos');
      return;
    }

    this.guardando = true;

    if (this.modoEdicion) {
      // Editar existente - Cargar el reciclaje completo primero
      const reciclajeOriginal = this.reciclajes.data.find(r => r.idReciclaje === this.reciclajeForm.id);

      if (!reciclajeOriginal) {
        alert('Error: No se encontró el reciclaje');
        this.guardando = false;
        return;
      }

      // Crear objeto con TODOS los campos del reciclaje original
      let reciclaje = new Reciclaje();
      reciclaje.idReciclaje = reciclajeOriginal.idReciclaje;
      reciclaje.peso = this.reciclajeForm.peso;
      reciclaje.tipo = this.reciclajeForm.tipo;
      reciclaje.metodo = this.reciclajeForm.metodo;
      reciclaje.fecha = reciclajeOriginal.fecha; // Mantener fecha original
      reciclaje.vecinoId = this.userId;

      console.log('Reciclaje a modificar:', reciclaje);

      this.reciclajeService.modificar(reciclaje).subscribe({
        next: (data) => {
          console.log('Reciclaje modificado:', data);
          alert('Reciclaje modificado correctamente');
          this.ngOnInit();// Recargar vecino para actualizar puntaje
          this.cerrarModal();
          this.guardando = false;
        },
        error: (error) => {
          console.error('Error al modificar:', error);
          alert('Error al modificar el reciclaje: ' + (error.error?.message || 'Error desconocido'));
          this.guardando = false;
        }
      });
    } else {
      // Crear nuevo
      let reciclaje = new Reciclaje();
      reciclaje.peso = this.reciclajeForm.peso;
      reciclaje.tipo = this.reciclajeForm.tipo;
      reciclaje.metodo = this.reciclajeForm.metodo;
      reciclaje.vecinoId = this.userId;

      console.log('Reciclaje a registrar:', reciclaje);

      this.reciclajeService.registrar(reciclaje).subscribe({
        next: (data) => {
          console.log('Reciclaje registrado:', data);
          alert('Reciclaje registrado exitosamente');
          this.ngOnInit(); // Recargar vecino para actualizar puntaje
          this.cerrarModal();
          this.guardando = false;
        },
        error: (error) => {
          console.error('Error al registrar:', error);
          alert('Error al registrar el reciclaje: ' + (error.error?.message || 'Error desconocido'));
          this.guardando = false;
        }
      });
    }
  }

  /**
   * Abre el modal para editar un reciclaje
   */
  editarReciclaje(reciclaje: Reciclaje): void {
    this.modoEdicion = true;
    this.reciclajeForm = {
      id: reciclaje.idReciclaje,
      peso: reciclaje.peso,
      tipo: reciclaje.tipo,
      metodo: reciclaje.metodo
    };
    console.log('Editando reciclaje:', this.reciclajeForm);
    this.mostrarModal = true;
  }

  /**
   * Elimina un reciclaje
   */
  eliminarReciclaje(id: number): void {
    if (!id) {
      alert('Error: ID de reciclaje no válido');
      return;
    }

    if (confirm('¿Estás seguro de eliminar este reciclaje?')) {
      console.log('Eliminando reciclaje con ID:', id);

      this.reciclajeService.eliminar(id).subscribe({
        next: () => {
          this.ngOnInit();
          console.log('Reciclaje eliminado exitosamente');
          alert('Reciclaje eliminado correctamente');
          // Recargar vecino para actualizar puntaje
        },
        error: (error) => {
          console.error('Error al eliminar:', error);
          console.error('Detalles del error:', error.error);
          alert('Error al eliminar el reciclaje: ' + (error.error?.message || error.message || 'Error desconocido'));
        }
      });
    }
  }

  // ==================== UTILIDADES ====================

  /**
   * Formatea una fecha para input type="date"
   */
  formatearFechaParaInput(fecha: Date | string): string {
    const date = new Date(fecha);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }


  // ==================== PAGINATOR ====================
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngAfterViewInit() {
    this.reciclajes.paginator = this.paginator;

    this.paginator.page.subscribe((event: PageEvent) => {
    });
  }

  get reciclajesPaginados() {
    if (!this.paginator) return this.reciclajes.data.slice(0, 5); // fallback
    const pageIndex = this.paginator.pageIndex;
    const pageSize = this.paginator.pageSize;
    return this.reciclajes.data.slice(pageIndex * pageSize, (pageIndex + 1) * pageSize);
  }
}
