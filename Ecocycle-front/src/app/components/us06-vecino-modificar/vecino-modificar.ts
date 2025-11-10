import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormField} from '@angular/material/form-field';
import {MatInput, MatLabel} from '@angular/material/input';
import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {VecinoService} from '../../services/vecino-service';
import {Router, RouterLink} from '@angular/router';
import {Vecino} from '../../model/vecino';

@Component({
  selector: 'app-us06-vecino-modificar',
  imports: [
    FormsModule,
    MatFormField,
    MatInput,
    MatLabel,
    MatOption,
    MatSelect,
    ReactiveFormsModule,
    RouterLink,
  ],
  templateUrl: './vecino-modificar.html',
  styleUrl: './vecino-modificar.css',
})
export class VecinoModificar {
  private vecinoService: VecinoService = inject(VecinoService);
  private router: Router = inject(Router);

  formModificar: FormGroup;
  private fb = inject(FormBuilder);

  constructor() {
    this.formModificar = this.fb.group({
      dni: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      nombre: ['', Validators.required],
      edad: ['', [Validators.required, Validators.min(15)]],
      genero: ['', Validators.required],
      distrito: ['', Validators.required],
      contrasena: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.vecinoService.buscarPorID(1).subscribe({
      next: data => {
        this.formModificar.patchValue(data);
      }
    })
  }

  modificar(){
    let vecino = new Vecino();
    vecino = this.formModificar.value;
    vecino.idVecino=1;
    this.vecinoService.modificar(vecino).subscribe({
      next: data => {
        console.log(data);
        this.router.navigate(['/perfil']);
      },
      error: err => {
        console.log(err);
      }
    })
  }
}
