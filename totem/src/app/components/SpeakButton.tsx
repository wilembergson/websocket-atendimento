'use client';

export default function SpeakButton() {
  const speak = () => {
    const utterance = new SpeechSynthesisUtterance('Ficha FI12, guichê 2.');
    utterance.lang = 'pt-BR'; // Português do Brasil
    utterance.rate = 1; // Velocidade da fala
    utterance.pitch = 1.6; // Tom da voz

    speechSynthesis.speak(utterance);
  };

  return (
    <button
      onClick={speak}
      className="bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded"
    >
      Falar com voz
    </button>
  );
}
