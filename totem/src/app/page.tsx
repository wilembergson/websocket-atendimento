'use client'
import { useEffect, useState } from "react";
import ListarEmAtendimento from "./components/ListarEmAtendimento";
import { useSearchParams } from "next/navigation";
import api from "@/api/api";
import TextoSuperior from "./components/TextoSuperior";

export default function Home() {
  const params = useSearchParams()
  const idLocal = params.get('idLocal')
  const [localNome, setLocalNome] = useState("")

  async function obterNomeLocal() {
    try {
      const promise = await api.buscarPainel(parseInt(idLocal!))
      setLocalNome(promise.data.nome)
    } catch (error: any) {
      console.log(error)
      alert("Erro ao carregar o painel")
    }
  }

  const [currentTime, setCurrentTime] = useState<string>('');
  useEffect(() => {
    obterNomeLocal()
    const updateTime = () => {
      const now = new Date();
      setCurrentTime(
        now.toLocaleTimeString([], {
          hour: '2-digit',
          minute: '2-digit',
          second: '2-digit',
        })
      );
    };

    updateTime();
    const intervalId = setInterval(updateTime, 1000);
    return () => clearInterval(intervalId);
  }, [])

  return (
    <main className="flex flex-col items-center h-screen">
      <section className="flex w-full">
        <TextoSuperior texto={localNome} />
        <TextoSuperior texto={currentTime} />
      </section>
      <ListarEmAtendimento idLocal={parseInt(idLocal!)} />
    </main>
  );
}
