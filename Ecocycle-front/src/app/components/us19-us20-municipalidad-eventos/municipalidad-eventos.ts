import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';
import {Evento} from '../../model/evento';
import {DatePipe} from '@angular/common';
import {EventoService} from '../../services/evento-service';
import {MatTableDataSource} from '@angular/material/table';
import {MatFormField, MatLabel, MatSuffix} from '@angular/material/form-field';
import {
  MatDatepickerModule,
  MatDatepickerToggle,
  MatDatepicker,
  MatDatepickerInput
} from '@angular/material/datepicker';
import {DateAdapter, MAT_DATE_LOCALE, MatNativeDateModule} from '@angular/material/core';
import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {MatInput} from '@angular/material/input';
import {Municipalidad} from '../../model/municipalidad';
import {MunicipalidadService} from '../../services/municipalidad-service';

@Component({
  selector: 'app-us19-us20-municipalidad-eventos',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    DatePipe,
    MatFormField,
    MatDatepickerToggle,
    MatDatepicker,
    MatDatepickerInput,
    MatDatepickerModule,
    MatNativeDateModule,
    MatLabel,
    MatOption,
    MatSelect,
    MatInput,
    MatSuffix,
  ],
  templateUrl: './municipalidad-eventos.html',
  styleUrl: './municipalidad-eventos.css',
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'es-PE' },
    DatePipe
  ]
})
export class MunicipalidadEventos {
  imgSrc = '/btn-crear-1.png';

  formFiltro: FormGroup;
  private fb = inject(FormBuilder);
  private eventoService: EventoService = inject(EventoService);
  private datePipe= inject(DatePipe);
  private municipalidadService: MunicipalidadService = inject(MunicipalidadService);
  private userId = Number(localStorage.getItem('userId'));
  private municipalidad:Municipalidad = new Municipalidad();

  constructor(private dateAdapter: DateAdapter<Date>) {
    this.dateAdapter.setLocale('es-PE');

    this.formFiltro = this.fb.group({
      distrito:[''],
      nombre: [''],
      tipo: [''],
      metodo: [''],
      fechaInicio: [''],
      fechaFin: ['']
    });
  }

  eventos: MatTableDataSource<Evento> = new MatTableDataSource<Evento>();

  ngOnInit() {
    this.municipalidadService.buscarPorId(this.userId).subscribe({
      next: data => {
        this.municipalidad = data;
        this.listarEventos();
      },
      error: err => {
        console.log(err);
      }
    })
  }


  listarEventos () {
    const filtros = {...this.formFiltro.value}
    filtros.distrito = this.municipalidad.distrito;

    if (filtros.fechaInicio) {
      filtros.fechaInicio = this.datePipe.transform(filtros.fechaInicio, 'yyyy-MM-dd');
    }
    if (filtros.fechaFin) {
      filtros.fechaFin = this.datePipe.transform(filtros.fechaFin, 'yyyy-MM-dd');
    }

    console.log(filtros);
    this.eventoService.listarPorDistrito(filtros).subscribe({

      next: (data) => {
        this.eventos.data = data;
        console.log("Eventos cargados: ", data);
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}
