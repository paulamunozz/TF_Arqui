import {Component, inject} from '@angular/core';
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
    ReactiveFormsModule
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

  filtrarRanking(){
    const filtros = {...this.formFiltro.value}

    console.log(filtros);
    this.vecinoService.ranking(filtros).subscribe({
      next: data => {
        console.log(data)
        this.dataSourceRanking.data = data;
      },
      error: error => {
        console.log(error);
      }
    })
  }
}
