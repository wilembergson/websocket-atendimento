import ListarAguardando from "./components/ListarAguardando";
import ListarEmAtendimento from "./components/ListarEmAtendimento";

export default function Home() {
  return (
      <main className="flex flex-col items-center">
        <h1>Websocket</h1>
        <ListarEmAtendimento/>
        <ListarAguardando/>
      </main>
  );
}
