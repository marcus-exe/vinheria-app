# 📱 Vinheria App - CRUD de Produtos

🌐 Idiomas disponíveis: [English](README.md) | [Português](README.pt-br.md)

Um aplicativo Android para gerenciamento de produtos com suporte a imagens, construído utilizando **práticas modernas de desenvolvimento Android**, como **arquitetura MVI**, **Jetpack Compose**, **Hilt** e **Room (KSP)**.

---
## Prévia

Início | Detalhes | Editar
:---:|:---:|:---:
<img src="./documents/home_screen.png" width="250"/> | <img src="./documents/details_screen.png" width="250"/> | <img src="./documents/edit_screen.png" width="250"/>

___

## 📌 Funcionalidades

✅ CRUD completo de produtos (Criar, Ler, Atualizar, Deletar)<br>
✅ Seletor de Imagens: Escolha imagens da **Galeria** ou **Câmera**<br>
✅ Banco de Dados Room pré-carregado (`.db` na pasta `/assets/`)<br>
✅ Padrão de Arquitetura MVI<br>
✅ Injeção de Dependências com **Hilt**<br>
✅ UI com Jetpack Compose<br>

---

## 🗃️ Banco de Dados - Room (com KSP)

* **Tecnologia:** Room + Kotlin Symbol Processing (KSP)  
* **Schema da Entidade:**  

```kotlin
@Entity
data class Product(
    @PrimaryKey val id: UUID,
    val name: String,
    val price: BigDecimal,
    val stock: Int,
    val description: String,
    val imgSrc: String? = null
)
````

* **Banco Pré-Carregado:**
  O app já vem com um banco `.db` populado inicialmente, localizado em:

```
app/src/main/assets/appDb.db
```

Carregado em tempo de execução via:

```kotlin
Room.databaseBuilder(app, Database::class.java, "appDb")
    .createFromAsset("appDb.db")
    .build()
```

---

## 🖼️ Manipulação de Imagens

* **Fontes:**

    * **Galeria (URIs de conteúdo)**
    * **Câmera (arquivos salvos no armazenamento interno do app `/data/user/0/.../files/`)**

* **Estratégia de Armazenamento:**
  Os caminhos das imagens são salvos como `String` no campo `imgSrc` do banco.

* **Exibição:**
  A biblioteca Coil (`rememberAsyncImagePainter` + `AsyncImage`) é usada para carregar as imagens na UI do Jetpack Compose.

* **Suporta:**

    * Arquivos locais (`file://...`)
    * Assets (`file:///android_asset/...`)
    * Content URIs (`content://...`)

---

## 💉 Injeção de Dependências - Hilt

* Projeto totalmente configurado com **Hilt**.
* Fornece:

    * Instância do banco Room
    * DAOs
    * ViewModels com Repositórios e UseCases injetados

---

## 🏗️ Arquitetura - MVI (Model-View-Intent)

O app segue um **fluxo unidirecional de dados** baseado na arquitetura **MVI**, com as seguintes camadas:

* **Model:**
  Mantém o estado (`ProductState`) e os dados.

* **View:**
  UI pura em **Jetpack Compose**, observa o estado vindo do ViewModel.

* **Intent / Eventos:**
  A UI dispara **eventos** (exemplo: `ProductEvent.AddProduct`, `ProductEvent.SetProductImageSrc`), que são processados no ViewModel.

* **ViewModel:**
  Processa os eventos, atualiza o estado (`MutableStateFlow`), e se comunica com o Repositório/Banco de Dados.

---

## 🎨 UI - Jetpack Compose

* Toda a interface construída em **Jetpack Compose**.
* Componentes baseados em Material Design 3.
* Segue boas práticas como **StateFlow**, **collectAsState()**, **Recomposição com Composables**, etc.

---

## ✅ Como Executar

1. Clone o repositório:

```bash
git clone https://github.com/marcus-exe/vinheria-app.git
```

2. Abra no **Android Studio Giraffe** (ou mais recente).

3. Sincronize o Gradle e faça o build.

4. Execute no emulador ou em um dispositivo físico.

---

## 📂 Observações

* **Primeira Execução:**
  Após a instalação, o app irá popular o banco de dados a partir de `assets/appDb.db`.

* **Persistência de Imagens:**
  As imagens da câmera são armazenadas de forma persistente no armazenamento interno (`filesDir`).
  As imagens da galeria usam **permissões persistentes de URI**.

* **Comportamento em Reset:**
  Caso o usuário **desinstale** o app, o banco pré-carregado será carregado novamente na próxima instalação.

* **Dicas com ADB:**
  Para depurar o banco Room ou os arquivos de imagem, você pode extraí-los de:

  ```
  /data/data/{seu.package.name}/databases/
  /data/data/{seu.package.name}/files/
  ```

---

## 🛠️ Tecnologias Utilizadas

* **Kotlin**
* **Room (KSP)**
* **Hilt**
* **Jetpack Compose**
* **Coil**
* **Arquitetura MVI**

---

📄 Licença

Este projeto está licenciado sob a Licença MIT.

```
MIT License

Copyright (c) 2025

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
