import {Component, inject, ViewChild} from '@angular/core';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatSelect} from '@angular/material/select';
import {MatOption} from '@angular/material/core';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTable,
  MatTableDataSource
} from '@angular/material/table';
import {Vecino} from '../../model/vecino';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatInput} from '@angular/material/input';
import {VecinoService} from '../../services/vecino-service';
import {MatPaginator} from '@angular/material/paginator';

@Component({
  selector: 'app-us32-ranking-vecino',
  imports: [
    MatFormField,
    MatLabel,
    MatOption,
    MatSelect,
    FormsModule,
    MatInput,
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    MatTable,
    MatHeaderCellDef,
    ReactiveFormsModule,
    MatPaginator,
  ],
  templateUrl: './ranking-vecino.html',
  styleUrl: './ranking-vecino.css',
})
export class RankingVecino {
  private vecinoService : VecinoService = inject(VecinoService);
  private fb = inject(FormBuilder);
  formFiltro: FormGroup;

  constructor() {
    this.formFiltro = this.fb.group({
      distrito:[''],
      genero:[''],
      edadMin:[''],
      edadMax:['']
    });
  }

  columnasRanking: string[]=["puesto", "nombre", "puntajetotal", "distrito"];
  dataSourceRanking:MatTableDataSource<Vecino>=new MatTableDataSource<Vecino>();

  ngOnInit() {
    this.filtrarRanking();
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  ngAfterViewInit() {
    this.dataSourceRanking.paginator = this.paginator;
  }

  filtrarRanking(){
    const filtros = {...this.formFiltro.value}

    console.log(filtros);
    this.vecinoService.ranking(filtros).subscribe({
      next: data => {
        console.log(data)
        this.dataSourceRanking.data = data.sort((a, b) => a.puesto - b.puesto);
      },
      error: error => {
        console.log(error);
      }
    })
  }
}
