'use client'
import ListarAguardando from "./components/ListarAguardando";
import ListarEmAtendimento from "./components/ListarEmAtendimento";
import { useSearchParams } from "next/navigation";

export default function Home() {

  const params = useSearchParams()
  const idLocal = params.get('idLocal')

  return (
      <main className="flex flex-col items-center">
        <h1>Websocket</h1>
        <ListarEmAtendimento idLocal={parseInt(idLocal!)}/>
        <ListarAguardando idLocal={parseInt(idLocal!)}/>
      </main>
  );
}
