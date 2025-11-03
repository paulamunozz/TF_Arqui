import {Component, inject, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';
import {Evento} from '../../model/evento';
import {DatePipe} from '@angular/common';
import {EventoService} from '../../services/evento-service';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';

@Component({
  selector: 'app-us19-us20-municipalidad-eventos',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    DatePipe,
    MatPaginator,
  ],
  templateUrl: './municipalidad-eventos.html',
  styleUrl: './municipalidad-eventos.css',
})
export class MunicipalidadEventos {
  imgSrc = '/btn-crear-1.png';

  formFiltro: FormGroup;
  private fb = inject(FormBuilder);
  private eventoService: EventoService = inject(EventoService);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor() {
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
    this.listarEventos();
  }

  ngAfterViewInit() {
    this.eventos.paginator = this.paginator;
  }

  listarEventos () {
    this.eventoService.listarPorDistrito(this.formFiltro.value).subscribe({
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
