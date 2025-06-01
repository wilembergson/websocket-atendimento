'use client';

import { useEffect, useState } from 'react';
import { CompatClient, Stomp } from '@stomp/stompjs';
import api, { API_URL } from '@/api/api';
import ItemAtendimento from './ItemAtendimento';
import SockJS from 'sockjs-client';

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

type Props = {
  readonly idLocal: number
}

export default function ListarEmAtendimento({idLocal}: Props) {

  const [painel, setPainel] = useState<ExibirPainel>()
  const [falando, setFalando] = useState(false)

  /*function som(ficha: string){
    const utterance = new SpeechSynthesisUtterance(`Ficha ${ficha}, guichê 02.`);
    utterance.lang = 'pt-BR'; // Português do Brasil
    utterance.rate = 0.8; // Velocidade da fala
    utterance.pitch = 1.6; // Tom da voz

    speechSynthesis.speak(utterance);
  }

  function som(ficha: string) {
  const texto = `Ficha ${ficha}, guichê 02.`;

  if (typeof window !== 'undefined' && (window as any).responsiveVoice) {
    const rv = (window as any).responsiveVoice;

    if (rv.voiceSupport()) {
      rv.speak(texto, 'Brazilian Portuguese', {
        rate: 0.8,
        pitch: 1.6,
        onstart: () => console.log('Fala iniciada'),
        onend: () => console.log('Fala finalizada'),
        onerror: () => console.error('Erro na fala')
      });
    } else {
      console.warn('Navegador não suporta responsiveVoice.');
    }

  } else {
    console.warn('responsiveVoice não está disponível no momento.');
  }
  }*/

    async function listarAtendimentos(){
      try{
        const promise = await api.listar(idLocal)
            console.log(promise.data)
            setPainel(promise.data)
            som(promise.data.chamada.identificacao, promise.data.chamada.tipo)
        } catch(e: any){
            console.log(e)
            localStorage.clear()
        }
    }

    function som(ficha: string, tipo:string){
      const textToSpeak = `Ficha ${ficha}, ${tipo}`
       console.log('Falando:', textToSpeak);
       

       (window as any).responsiveVoice.speak(textToSpeak, 'Brazilian Portuguese Female', {
         pitch: 1,
         rate: 0.8,
         volume: 1,
         onstart: () => {
          setFalando(true)
          console.log('Iniciando fala...')
         },
         onend: () => {
          setFalando(false)
          console.log('Fala concluída.')
         },
       });
     }

    useEffect(() => {
    const scriptId = 'responsivevoice-script';

    if (!document.getElementById(scriptId)) {
      const script = document.createElement('script');
      script.id = scriptId;
      script.src =
        'https://code.responsivevoice.org/responsivevoice.js?key=aaaaaaaa';
      script.async = true;

      script.onload = () => {
        if ((window as any).responsiveVoice) {
          (window as any).responsiveVoice.init();
          //setIsVoiceReady(true);
        }
      };

      script.onerror = () => {
        console.error('Falha ao carregar o script ResponsiveVoice.');
      };

      document.body.appendChild(script);
    } else {
      if ((window as any).responsiveVoice) {
        (window as any).responsiveVoice.init();
        (window as any).responsiveVoice.setDefaultVoice('Brazilian Portuguese Female');
        //setIsVoiceReady(true);
      }
    }
  }, []);

  // useEffect(() => {
  //   if (voiceReady) {
  //     const textToSpeak = 'Testando o som'
  //     console.log('Falando:', textToSpeak);

  //     (window as any).responsiveVoice.speak(textToSpeak, 'Brazilian Portuguese Female', {
  //       pitch: 1,
  //       rate: 0.8,
  //       volume: 1,
  //       onstart: () => console.log('Iniciando fala...'),
  //       onend: () => console.log('Fala concluída.'),
  //     });
  //   }
  // }, [voiceReady]);

  useEffect(() => {
    //listarAtendimentos()
    const socket = new SockJS(`${API_URL}/ws`);
    const stompClient: CompatClient = Stomp.over(socket);

    stompClient.connect({}, () => {
      stompClient.subscribe(`/topic/exibir-painel-${idLocal}`, (message) => {
        console.log(message.body)
        listarAtendimentos()
      });
    });

    return () => {
      stompClient.disconnect(() => {});
    };
  }, [])

  return (
    <div className='flex w-full h-full'>
      <div className='flex m-4 w-1/2'>
        <section className={`flex flex-col text-white w-full justify-center items-center rounded-lg font-bold ${falando ? "bg-amber-600":"bg-gray-700"} transition-colors duration-300`} >
          <h1 className='flex text-[3rem] sm:text-[5rem] md:text-[7rem] lg:text-[12rem] xl:text-[16rem]'>
            {painel?.chamada?.identificacao}
          </h1>
          <h1 className='flex text-4xl'>
            {painel?.chamada?.tipo}
          </h1>
        </section>
      </div>
      <div className="flex flex-col w-1/2 m-4 items-center p-2 rounded-md bg-slate-500">
        {painel?.ultimasChamadas?.map((atendimento) => (
          <ItemAtendimento key={atendimento.id} atendimento={atendimento}/>
        ))}
      </div>
    </div>
  );
}
