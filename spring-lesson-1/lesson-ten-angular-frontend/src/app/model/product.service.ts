import { Injectable } from '@angular/core';
// @ts-ignore
import {Product, User} from "./product";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private identity: number = 6;

  private products: { [key: number]: Product } = {
    1: new Product(1, 'First Product', 18),
    2: new Product(2, 'Second Product', 27),
    3: new Product(3, 'Third Product', 34),
    4: new Product(4, 'Fourth Product', 52),
    5: new Product(5, 'Fifth Product', 21),
  };

  constructor(public http: HttpClient) { }

  public findAll() {
    return this.http.get<Product[]>('/api/v1/product/all').toPromise();
  }

  public findById(id: number) {
    return this.http.get<Product>(`api/v1/product/${id}`).toPromise();
  }

  public save(product: Product) {
    return new Promise<void>((resolve, reject) => {
      if (product.id == -1) {
        product.id = this.identity++;
      }
      this.products[product.id] = product;
      resolve();
    })
  }

  public delete(id: number) {
    return new Promise<void>((resolve, reject) => {
      delete this.products[id];
      resolve();
    })
  }

}
