'use client';

import { useEffect, useState } from 'react';
import { CompatClient, Stomp } from '@stomp/stompjs';
import api, { API_URL } from '@/api/api';
import ItemAtendimento from './ItemAtendimento';
import SockJS from 'sockjs-client';
import { useSearchParams } from 'next/navigation';

export interface Ficha {
  id: number;
  numero: number
  tipo: string
  identificacao: string;
}

export interface Guiche {
  id: number
  numero: number
}

export interface Atendimento {
  id:number
  identificacao: string
  tipo: string
  data: string
}

type ExibirPainel = {
  chamada: Atendimento,
  ultimasChamadas: Atendimento[]
  
}

export default function ListarEmAtendimento() {
  const params = useSearchParams()
  const idLocal = params.get('idLocal')
  const [painel, setPainel] = useState<ExibirPainel>();

  function som(ficha: string){
    const utterance = new SpeechSynthesisUtterance(`Ficha ${ficha}, guichê 02.`);
    utterance.lang = 'pt-BR'; // Português do Brasil
    utterance.rate = 0.8; // Velocidade da fala
    utterance.pitch = 1.6; // Tom da voz

    speechSynthesis.speak(utterance);
  };

    async function listarAtendimentos(){
        try{
            const promise = await api.listar()
            setPainel(promise.data)
            som(promise.data.chamada.ficha.identificacao)
        } catch(e: any){
            console.log(e)
        }
    }

  useEffect(() => {
    listarAtendimentos()
    const socket = new SockJS(`${API_URL}/ws`);
    const stompClient: CompatClient = Stomp.over(socket);

    stompClient.connect({}, () => {
      stompClient.subscribe(`/topic/exibir-painel-${idLocal}`, (message) => {
        listarAtendimentos()
      });
    });

    return () => {
      stompClient.disconnect(() => {});
    };
  }, []);

  return (
    <div className='flex w-full h-full'>
      <div className='flex m-4 w-1/2'>
        <section className='flex flex-col bg-slate-700 text-white w-full justify-center items-center rounded-lg font-bold'>
          <h1 className='flex text-[3rem] sm:text-[5rem] md:text-[7rem] lg:text-[12rem] xl:text-[16rem]'>
            {painel?.chamada?.identificacao}
          </h1>
          <h1 className='flex text-4xl'>
            {painel?.chamada?.tipo}
          </h1>
        </section>
      </div>
      <div className='flex flex-col w-1/2 m-4 items-center bg-gray-500 p-2 rounded-md'>
        {painel?.ultimasChamadas?.map((atendimento) => (
          <ItemAtendimento key={atendimento.id} atendimento={atendimento}/>
        ))}
      </div>
    </div>
  );
}
