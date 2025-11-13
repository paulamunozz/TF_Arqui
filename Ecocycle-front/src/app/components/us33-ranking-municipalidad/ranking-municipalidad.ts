import {Component, inject} from '@angular/core';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTable,
  MatTableDataSource
} from '@angular/material/table';
import {Municipalidad} from '../../model/municipalidad';
import {MunicipalidadService} from '../../services/municipalidad-service';

@Component({
  selector: 'app-us33-ranking-municipalidad',
  imports: [
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    MatTable,
    MatHeaderCellDef
  ],
  templateUrl: './ranking-municipalidad.html',
  styleUrl: './ranking-municipalidad.css',
})
export class RankingMunicipalidad {
  private municipalidadService: MunicipalidadService = inject(MunicipalidadService);
  columnasRanking: string[]=["puesto", "distrito", "puntaje"];
  dataSourceRanking:MatTableDataSource<Municipalidad>=new MatTableDataSource<Municipalidad>();

  ngOnInit() {
    this.mostrarRanking();
  }

  mostrarRanking(){
    this.municipalidadService.rankingMunicipalidades().subscribe({
      next: (data: Municipalidad[]) => {
        const rankingConPuestos = data.map((muni, index) => ({
          ...muni,
          puesto: index + 1,
        }));

        this.dataSourceRanking.data = rankingConPuestos;
        console.log('Ranking cargado exitosamente:', rankingConPuestos);
      },
      error: (err) => {
        console.error('Error al cargar el ranking. Verifique el token y la conexión al backend.', err);
        this.dataSourceRanking.data = [];
      }
    });
  }
}
