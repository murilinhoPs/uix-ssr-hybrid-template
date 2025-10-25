# UIx SSR Hybrid Template

Template minimalista para iniciar projetos com **Server-Side Rendering (SSR) híbrido** usando Clojure, ClojureScript e UIx (React). Tudo está configurado para entregar HTML renderizado no servidor, com hidratação de componentes reativos no cliente.

## O que vem pronto

- Servidor Ring/Jetty com rotas básicas (`/` e `/about`)
- Geração de HTML com Hiccup 2 e injeção de dados em JSON
- Metadados SEO iniciais (title, description, canonical) prontos para expandir
- Cliente UIx com exemplos simples de hero, cards e contador interativo
- Build de front-end via Shadow-CLJS apontando para `resources/public/js`
- Estilos básicos em `resources/public/css/main.css` para orientar a estrutura

## Pré-requisitos

- Java 11+
- [Clojure CLI](https://clojure.org/guides/getting_started)
- Node.js 16+ (Shadow-CLJS e dependências React)

## Instalação

```bash
npm install
```

As dependências Clojure são baixadas automaticamente no primeiro `clj -M`.

## Desenvolvimento

```bash
npm run dev
```

O comando executa:

1. `shadow-cljs watch app` – build e hot reload do bundle UIx
2. `clj -M -m app.server` – servidor Ring em `http://localhost:3000`

> Dica: rode `npm run server` e `npm run watch` em terminais separados se quiser logs isolados.

## Estrutura

```
src/app/
  server.clj   ; rotas e HTML SSR
  client.cljs  ; componentes UIx e hidratação

resources/public/
  css/main.css ; estilos base
  js/          ; saída do Shadow-CLJS (mantida vazia com .gitkeep)
```

## Personalização sugerida

- Atualize as rotas em `server.clj` para representar suas páginas reais
- Troque o conteúdo estático dos handlers `home-handler` e `about-handler`
- Crie novos componentes UIx ou remova os exemplos (hero, cards, contador)
- Ajuste o CSS de acordo com o design desejado
- Adicione metatags extras ou JSON-LD em `build-page-metadata` se precisar

## Deploy

O template não inclui scripts de deploy. Utilize a abordagem favorita (Docker, Heroku, Fly.io, Railway, etc.) chamando `clj -M -m app.server` e garantindo que o bundle `shadow-cljs release app` esteja disponível em `resources/public/js`.
