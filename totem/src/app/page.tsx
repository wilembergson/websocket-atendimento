import ListarEmAtendimento from "./components/ListarEmAtendimento";
import SpeakButton from "./components/SpeakButton";

export default function Home() {
  return (
    <main className="flex flex-col items-center h-screen">
      <SpeakButton />
      <ListarEmAtendimento />
    </main>
  );
}
