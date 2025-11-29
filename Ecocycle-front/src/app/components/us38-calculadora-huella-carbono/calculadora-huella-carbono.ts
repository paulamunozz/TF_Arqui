import {Component, inject} from '@angular/core';
import {CalculadoraHuellaCarbonoService} from '../../services/api/calculadora-huella-carbono-service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatCard, MatCardContent} from '@angular/material/card';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatSelect, MatOption} from '@angular/material/select';
import {MatInput} from '@angular/material/input';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-us38-calculadora-huella-carbono',
  imports: [
    MatCard,
    MatCardContent,
    MatFormField,
    ReactiveFormsModule,
    MatLabel,
    MatSelect,
    MatOption,
    MatInput,
    MatButton
  ],
  templateUrl: './calculadora-huella-carbono.html',
  styleUrl: './calculadora-huella-carbono.css',
})
export class CalculadoraHuellaCarbono {
  private calculadoraService:CalculadoraHuellaCarbonoService = inject(CalculadoraHuellaCarbonoService);

  formHuella: FormGroup;
  private fb = inject(FormBuilder);
  huella: string = "-";

  constructor() {
    this.formHuella = this.fb.group({
      transporte: ['', Validators.required],
      distancia: ['', [Validators.required, Validators.min(0)]],
    });
  }

  onSubmit(){
    if (this.formHuella.valid) {
      if (this.formHuella.controls['transporte'].value == "bus") {
        this.calculadoraService.huellaBus(this.formHuella.controls['distancia'].value).subscribe({
          next: (result:any) => {
            this.huella = Number(result.co2e/20).toFixed(2) + "  CO₂e";
          },
          error: err => {
            console.log(err);
          }
        })
      }
      else if (this.formHuella.controls['transporte'].value == "auto") {
        this.calculadoraService.huellaCarro(this.formHuella.controls['distancia'].value).subscribe({
          next: (result:any) => {
            this.huella = Number(result.co2e).toFixed(2) + "  CO₂e";
          },
          error: err => {
            console.log(err);
          }
        })
      }
      else if (this.formHuella.controls['transporte'].value == "avion") {
        this.calculadoraService.huellaCarro(this.formHuella.controls['distancia'].value).subscribe({
          next: (result:any) => {
            this.huella = Number(result.co2e).toFixed(2) + "  CO₂e";
          },
          error: err => {
            console.log(err);
          }
        })
      }
    }
  }
}

