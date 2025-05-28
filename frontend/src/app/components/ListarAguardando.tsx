'use client';

import { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { CompatClient, Stomp } from '@stomp/stompjs';
import api, { API_URL } from '@/api/api';
import ItemAtendimento from './ItemAtendimento';

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
  status: string
  data: string
  idFicha: number
  tipoFicha: string
  identificacaoFicha: string
  nomeLocal: string
}

export default function ListarAguardando() {
  const statusAguardando = "AGUARDANDO"
  const [aguardando, setAguardando] = useState<Atendimento[]>([]);

    async function listarAguardando(){
        try{
            const promise = await api.listar(statusAguardando)
            setAguardando(promise.data)
        } catch(e: any){
            console.log(e)
        }
    }

  useEffect(() => {
    listarAguardando()
    const socket = new SockJS(`${API_URL}/ws`);
    const stompClient: CompatClient = Stomp.over(socket);

    stompClient.connect({}, () => {
      stompClient.subscribe('/topic/alterar-status', (message) => {
        listarAguardando()
      });
    });

    return () => {
      stompClient.disconnect(() => {});
    };
  }, []);

  return (
    <div className='flex w-2/3'>
      <div className='flex flex-col w-full my-4 items-center bg-gray-500 p-2 rounded-md'>
        <h1>Aguardando: {aguardando.length}</h1>
        {aguardando.map((atendimento) => (
          <ItemAtendimento key={atendimento.id} atendimento={atendimento}/>
        ))}
      </div>
    </div>
  );
}
