import { Component, OnInit } from '@angular/core';
import {ProductService} from "../model/product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Product} from "../model/product";

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.scss']
})
export class ProductFormComponent implements OnInit {

  public user = new Product(-1, "", 0);
  public isError:boolean = false;

  constructor(private userService: ProductService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      if (param.id == 'new') {
        this.user = new Product(-1, "", 0);
        return;
      }
      this.userService.findById(param.id)
        .then(res => {
          this.isError = false;
          this.user = res;
        })
        .catch(err => {
          console.error(err);
          this.isError = true;
        });
    })
  }

  public save() {
    this.userService.save(this.user)
      .then(() => {
        this.isError = false;
        this.router.navigateByUrl('/product');
      })
      .catch(err => {
        console.error(err);
        this.isError = true;
      });
  }

}
