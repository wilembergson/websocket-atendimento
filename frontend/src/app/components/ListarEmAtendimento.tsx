'use client';

import { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { CompatClient, Stomp } from '@stomp/stompjs';
import api, { API_URL } from '@/api/api';
import ItemAtendimento from './ItemAtendimento';
import { Atendimento } from './ListarAguardando';

type Props = {
  idLocal: number
}

export default function ListarEmAtendimento({ idLocal }: Props) {
  const statusEmAtendimento = "EM_ATENDIMENTO"
  const [atendimentos, setAtendimentos] = useState<Atendimento[]>([]);

  async function listarAtendimentos() {
    try {
      const promise = await api.listar(statusEmAtendimento, idLocal)
      setAtendimentos(promise.data)
    } catch (e: any) {
      console.log(e)
    }
  }

  useEffect(() => {
    listarAtendimentos()
  }, [idLocal])

  useEffect(() => {
    listarAtendimentos()
    const socket = new SockJS(`${API_URL}/ws`);
    const stompClient: CompatClient = Stomp.over(socket);

    stompClient.connect({}, () => {
      stompClient.subscribe('/topic/alterar-status', (message) => {
        listarAtendimentos()
      });
    });

    return () => {
      stompClient.disconnect(() => { });
    };
  }, []);

  return (
    <div className='flex w-2/3'>
      <div className='flex flex-col w-full my-4 items-center bg-gray-500 p-2 rounded-md'>
        <h1>Em atendimento: {atendimentos.length}</h1>
        {atendimentos.map((atendimento) => (
          <ItemAtendimento key={atendimento.id} atendimento={atendimento} />
        ))}
      </div>
    </div>
  );
}
