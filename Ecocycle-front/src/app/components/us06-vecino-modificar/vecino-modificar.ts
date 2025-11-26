import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormField} from '@angular/material/form-field';
import {MatInput, MatLabel} from '@angular/material/input';
import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {VecinoService} from '../../services/vecino-service';
import {Router, RouterLink} from '@angular/router';
import {Vecino} from '../../model/vecino';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

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
    MatIcon,
    MatIconButton,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './vecino-modificar.html',
  styleUrl: './vecino-modificar.css',
})
export class VecinoModificar {
  private vecinoService: VecinoService = inject(VecinoService);
  private router: Router = inject(Router);
  private userId:number = Number(localStorage.getItem('userId'));

  formModificar: FormGroup;
  private fb = inject(FormBuilder);

  fotos = [
    '/icono-default.png',
    '/icono-1.png',
    '/icono-2.png',
    '/icono-3.png'
  ];

  constructor() {
    this.formModificar = this.fb.group({
      dni: ['', Validators.pattern('^[0-9]{8}$')],
      nombre: [''],
      edad: ['', Validators.min(15)],
      genero: [''],
      distrito: [''],
      contrasena: [''],
      icono: ['']
    });
  }

  ngOnInit() {
    this.vecinoService.buscarPorID(this.userId).subscribe({
      next: data => {
        const { contrasena, ...vecinoSinContrasena } = data;
        this.formModificar.patchValue(vecinoSinContrasena);
      }
    })
  }

  modificar(){
    let vecino = new Vecino();
    vecino = this.formModificar.value;
    vecino.idVecino=this.userId;
    this.vecinoService.modificar(vecino).subscribe({
      next: data => {
        console.log(data);
        this.vecinoService.setIcono(data.icono);
        this.router.navigate(['/perfil']);
      },
      error: err => {
        console.log(err);
      }
    })
  }

  hidePassword = true;

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  seleccionarFoto(index: number) {
    this.formModificar.controls['icono'].setValue(index);
  }
}
