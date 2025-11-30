import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';
import {EventoService} from '../../services/evento-service';
import {DatePipe} from '@angular/common';
import {DateAdapter, MAT_DATE_LOCALE} from '@angular/material/core';
import {MatTableDataSource} from '@angular/material/table';
import {Evento} from '../../model/evento';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import {MatButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-us27-vecino-eventos-registrados',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    DatePipe,
    MatButton,
    MatIcon,
  ],
  templateUrl: './vecino-eventos-registrados.html',
  styleUrl: './vecino-eventos-registrados.css',
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'es-PE' },
    DatePipe
  ]
})
export class VecinoEventosRegistrados {
  formFiltro: FormGroup;
  private fb = inject(FormBuilder);
  private eventoService: EventoService = inject(EventoService);
  private datePipe= inject(DatePipe);
  private userId = Number(localStorage.getItem('userId'));

  constructor(private dateAdapter: DateAdapter<Date>) {
    this.dateAdapter.setLocale('es-PE');

    this.formFiltro = this.fb.group({
      vecinoId:[this.userId],
      nombre: [''],
      tipo: [''],
      metodo: [''],
      fechaInicio: [''],
      fechaFin: ['']
    });
  }

  eventos: MatTableDataSource<Evento> = new MatTableDataSource<Evento>();

  ngOnInit() {
    this.listarEventos();
  }

  listarEventos () {
    const filtros = {...this.formFiltro.value}

    if (filtros.fechaInicio) {
      filtros.fechaInicio = this.datePipe.transform(filtros.fechaInicio, 'yyyy-MM-dd');
    }
    if (filtros.fechaFin) {
      filtros.fechaFin = this.datePipe.transform(filtros.fechaFin, 'yyyy-MM-dd');
    }

    console.log(filtros);
    this.eventoService.listarPorVecino(filtros).subscribe({

      next: (data) => {
        this.eventos.data = data;
        console.log("Eventos cargados: ", data);
      },
      error: (error) => {
        console.log(error);
        alert(error.error?.message || 'Error desconocido');
      }
    })
  }

  limpiarFiltros(): void {
    this.formFiltro.reset({
      vecinoId: this.userId,
      nombre: '',
      tipo: '',
      metodo: '',
      fechaInicio: '',
      fechaFin: ''
    });
    this.listarEventos();
  }
}
